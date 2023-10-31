package com.examples.gestures;

// I recommend watching this webinar about this topic:
// Swiping your way through Appium by Wim Selles
// https://www.youtube.com/watch?v=oAJ7jwMNFVU
// The demo App:
// https://github.com/saucelabs/my-demo-app-android

import com.google.common.collect.ImmutableMap;
import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.helpers.Constants.*;

/**
 * Android Native App Tests
 */
public class GestureSwipeAndroidNativeAppTest {

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;
    By productsTitle = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    By allProductsOfCatalog = By.id("com.saucelabs.mydemoapp.android:id/productRV");

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
        capabilities.setCapability("appium:platformVersion", "1[2-3]");
        String appName = "my-demo-app-android.apk";
        capabilities.setCapability("appium:app", "storage:filename=" +appName);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo", "gestures-swipe");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        sauceOptions.setCapability("resigningEnabled", true);
        sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
        sauceOptions.setCapability("appiumVersion", "2.0.0");

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
    public void swipeUp() throws MalformedURLException {

        // From:
        // https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md

        // waiting for the page to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));


        // Get the screen dimension
        Dimension size = driver.manage().window().getSize();
        int screenWidth = size.width;
        int screenHeight = size.height;

        System.out.println("Screen Width: " + screenWidth);
        System.out.println("Screen Height: " + screenHeight);


        //https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md#mobile-swipegesture
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "left", 0, "top", 0, "width", screenWidth, "height", screenHeight,
                "direction", "up",
                "percent", 1.00
        ));

        waiting(5); // For video and demo purpose

    }

    @Test
    public void swipeUpOnElement() throws MalformedURLException {

        // From:
        // https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md

        // waiting for the page to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));


        WebElement element = (WebElement) driver.findElement(allProductsOfCatalog);
        String elementId = ((RemoteWebElement) element).getId();

        //https://github.com/appium/appium-uiautomator2-driver/blob/master/docs/android-mobile-gestures.md#mobile-swipegesture
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", elementId,
                "direction", "up",
                "percent", 1.00
        ));

        waiting(5); // For video and demo purpose

    }

    @Test
    public void swipeUpUsingUIAutomator() throws MalformedURLException {

        // waiting for the page to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));

        // Find element by the element text -> if needed - swipe to this element
        WebElement product = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"Sauce Labs Onesie\"));"));
        waiting(5); // For video and demo purpose
        System.out.println("Sauce - the product text is " + product.getText());
    }

    @Test
    public void swipeUpUsingW3CActions() throws MalformedURLException {

        // Good article: https://www.linkedin.com/pulse/guide-perform-appium-20-w3c-mobile-actions-swipe-ꜱᴀɴᴋᴇᴛ-ᴊᴏꜱʜɪ
        // This swipe can be used for both Android and iOS.

        // waiting for the page to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsTitle));


        // 1. The rectangle of the element to scroll
        WebElement element = (WebElement) driver.findElement(allProductsOfCatalog);
        Rectangle rect = element.getRect();

        // 2. Determine the x and y position of initial touch
        // we scroll up/down and the x doesn't change
        int centerX = rect.x + (int)(rect.width /2);

        int startY;
        int endY;

        // Swipe Up
        startY = rect.y + (int) (rect.height * 0.8);
        // The end Y position
        endY = rect.y + (int) (rect.height * 0.2);


        // 3. swipe
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
        driver.perform(Collections.singletonList(tapPoint));

        waiting(5); // For video and demo purpose

    }

    private void waiting(int sec){
        try
        {
            Thread.sleep(sec*1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }


}
