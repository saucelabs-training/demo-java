package com.examples.gestures;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.helpers.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Android Native App Tests
 */
public class GesturesAndroidNativeAppTest {

    By productsScreenLocator = By.xpath("//*[@content-desc=\"products screen\"]");
    By productScreenLocator = By.xpath("//*[@content-desc=\"product screen\"]");

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

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
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        //Allocate any avilable samsung device with Android version 12
        capabilities.setCapability("appium:deviceName", "Samsung.*");
        capabilities.setCapability("appium:platformVersion", "12");
        String appName = "Android.MyDemoAppRN.apk";
        capabilities.setCapability("appium:app", "storage:filename=" +appName);
        capabilities.setCapability("appium:appWaitActivity","com.saucelabs.mydemoapp.rn.MainActivity");

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo", "gestures");
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
            return;
        }
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }


    @Test
    public void scrollAndSelectProduct() throws MalformedURLException {
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
        WebElement product = getProduct("Test.allTheThings");
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

    private WebElement getProduct(String needle){
        By elemLocator =  By.xpath("//android.widget.TextView[contains(@text,'" + needle + "')]");
        // swipe if needed
        for (int i = 0; i < 2; i++) {
            if (isDisplayed(elemLocator,1))
                return driver.findElement(elemLocator);;
            // swipe
            scrollElementUP(driver.findElement(productsScreenLocator));
        }
        return null;
    }

    /**
     * Performs scroll or swipe inside an element
     * @param el  the element to swipe
     * @version java-client: 8.2.0
     **/
    public void scrollElementUP(WebElement el) {

        // 1. The rectangle of the element to scroll
        Rectangle rect = el.getRect();

        // 2. Determine the x and y position of initial touch
        // we scroll up and the x doesn't change
        int centerX = rect.x + (int)(rect.width /2);
        // The starting Y position
        int startY = rect.y + (int)(rect.height*0.9);
        // The end Y position
        int endY = rect.y;

        // 3. swipe: https://appium.io/docs/en/commands/interactions/actions/
        // finger
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tapPoint = new Sequence(finger, 1);
        // Move finger into start position
        tapPoint.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX, startY));
        // Finger comes down into context with screen
        tapPoint.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Pause for a little bit
        tapPoint.addAction(new Pause(finger, Duration.ofMillis(100)));
        // Finger move to end position
        tapPoint.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), centerX, endY));
        // Finger gets up, off the screen
        tapPoint.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the scroll
        driver.perform(Arrays.asList(tapPoint));

        // always allow scroll action to complete
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
        int startY = rect.y + (int)(rect.height /2);


        // 3. tap: https://appium.io/docs/en/commands/interactions/actions/
        // finger
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tapPoint = new Sequence(finger, 1);
        // Move finger into start position
        tapPoint.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), centerX, startY));
        // Finger comes down into context with screen
        tapPoint.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        // Finger gets up, off the screen
        tapPoint.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Perform the tap
        driver.perform(Arrays.asList(tapPoint));

        // always allow scroll action to complete
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // ignore
        }
    }

}
