package com.examples.gestures;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.helpers.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GesturesIOSNativeAppTest {

    By productsScreenLocator = By.id("products screen");
    By productScreenLocator = By.id("product screen");

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private IOSDriver driver;

    @Before
    public void setUp() throws MalformedURLException {

        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                break;
        }

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCuiTest");
        //Allocate any avilable iPhone device with version 14
        capabilities.setCapability("appium:deviceName", "iPhone.*");
        capabilities.setCapability("appium:platformVersion", "14");
        String appName = "iOS.MyDemoAppRN.ipa";
        capabilities.setCapability("app", "storage:filename=" +appName);
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo_ios", "iOS", "Demo");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        capabilities.setCapability("sauce:options", sauceOptions);

        try {
        driver = new IOSDriver(url, capabilities);
        } catch (Exception e){
            System.out.println("Error to create iOS Driver: " + e.getMessage());
        }

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }


    @Test
    public void scrollAndSelectProduct() throws MalformedURLException {
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
        WebElement product = getProduct("Test.allTheThings() T-Shirt");
        if (product !=null)
            tapElement(product);
//            product.click();
        else
            System.out.println("Can't find product Test.allTheThings");

        // Verify we select a product
        assertThat(isDisplayed(productScreenLocator, 10)).as("Verify product page").isTrue();
    }

    public Boolean isDisplayed(By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public Boolean isElementDisplayed(WebElement elem, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(elem));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    private WebElement getProduct(String needle){
        String selector = "**/XCUIElementTypeOther[`label == \"" + needle + "\"`]";
        // in iOS - XCUITest Gives back all elements  in the screen
        WebElement productElement = (WebElement) driver.findElement(AppiumBy.iOSClassChain(selector));
        // swipe if needed
        for (int i = 0; i < 2; i++) {
            if (isElementDisplayed(productElement,1))
                return productElement;
            // swipe
            swipeElementUP(driver.findElement(productsScreenLocator));
        }
        return null;
    }

    /**
     * From: http://appium.io/docs/en/writing-running-appium/tutorial/swipe/simple-element/
     * Performs swipe inside an element
     *
     * @param el  the element to swipe
     * @version java-client: 8.2.0
     **/
    public void swipeElementUP(WebElement el) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("elementId",  ((RemoteWebElement)el).getId());
        params.put("direction", "down");
        driver.executeScript("mobile: scroll", params);

        // always allow swipe action to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public void tapElement(WebElement el) {

        // 1. The rectangle of the element to scroll
        Rectangle rect = el.getRect();

        // 2. Determine the x and y position of initial touch
        int centerX = rect.x + (int)(rect.width /2);
        int centerY = rect.y + (int)(rect.height /2);


        HashMap<String, Object> params = new HashMap<>();
        params.put("x", centerX);
        params.put("y", centerY);
        driver.executeScript("mobile: tap", params);

        // always allow swipe action to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
