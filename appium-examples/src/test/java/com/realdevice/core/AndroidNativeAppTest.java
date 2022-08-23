package com.realdevice.core;

import com.realdevice.SauceTestWatcher;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static helpers.Constants.region;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Android Native App Tests
 */
public class AndroidNativeAppTest {

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";

    By productsScreenLocator = By.xpath("//*[@content-desc=\"products screen\"]");
    By productScreenLocator = By.xpath("//*[@content-desc=\"product screen\"]");

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    private AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Sauce Android Native App  - BeforeMethod hook");
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                System.out.println("Sauce REGION US");
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                System.out.println("Sauce REGION EU");
                break;
        }

        //find a device in the cloud
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "UiAutomator2");
        //Allocate any avilable samsung device with Android version 12
        capabilities.setCapability("appium:deviceName", "Samsung.*");
        capabilities.setCapability("appium:platformVersion", "12");
        String appName = "Android.MyDemoAppRN.apk";
        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("appium:appWaitActivity","com.saucelabs.mydemoapp.rn.MainActivity");

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        sauceOptions.setCapability("resigningEnabled", true);
        sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);

        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            driver = new AndroidDriver(url, capabilities);
        } catch (Exception e){
            System.out.println("Error to create Android Driver: " + e.getMessage());
        }
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void verifyInProductsPage() throws MalformedURLException {
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
    }

    @Test
    public void selectProduct() throws MalformedURLException {
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
        WebElement product = getProduct("Backpack");
        if (product !=null)
            product.click();
        else
            System.out.println("Can't find product Backpack");

        // Verify we select a product
        assertThat(isDisplayed(productScreenLocator, 10)).as("Verify product page").isTrue();
    }

    @Test
    public void scrollAndSelectProduct() throws MalformedURLException {
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
        WebElement product = getProduct("Test.allTheThings");
        if (product !=null)
            product.click();
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

    private WebElement getProduct(String needle){
        By elemLocator =  By.xpath("//android.widget.TextView[contains(@text,'" + needle + "')]");
        // swipe if needed
        for (int i = 0; i < 2; i++) {
            if (isDisplayed(elemLocator,1))
                return driver.findElement(elemLocator);;
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
     * @version java-client: 7.3.0
     **/
    public void swipeElementUP(WebElement el) {
        System.out.println("swipeElementAndroid() "); // always log your actions

        // Animation default time:
        //  - Android: 300 ms
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 500; // ms
        final int PRESS_TIME = 200; // ms

        PointOption pointOptionStart, pointOptionEnd;

        // init screen variables
        Rectangle rect = el.getRect();

        pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                rect.y + (int)(rect.height*0.9));
        pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                rect.y + (int)(rect.height*0.1) );

        // execute swipe using TouchAction
        try {
            new TouchAction(driver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeElementAndroid(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
