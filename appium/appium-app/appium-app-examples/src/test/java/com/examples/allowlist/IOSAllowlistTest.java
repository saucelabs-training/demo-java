package com.examples.allowlist;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
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

public class IOSAllowlistTest {

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private IOSDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        System.out.println("Sauce iOS Native App  - Before hook");

        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;
        String appName;

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
        // You need to open the default browser (Safari for iOS, Chrome for Android)
        // and not to install your app with the "app" capability
        capabilities.setCapability("browserName", "Safari");

        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo_ios", "iOS", "allowlist");
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
    public void openSettingsApp() throws MalformedURLException {


        // 1) open your app ( in this case Apple Preferences App).
        // The app is installed already on the device.
        driver.activateApp("com.apple.Preferences");

        // 2) Switch to the Native_APP context
        driver.context("NATIVE_APP");

        // 3) Run our buisness flow on the App
        // In this case - open the 'privacy' setting
        RemoteWebElement settingsTable = (RemoteWebElement)driver.findElement(By.xpath("//XCUIElementTypeApplication[@name=\"Settings\"]"));
        String elementID = settingsTable.getId();

        HashMap<String, String> scrollObject = new HashMap<String, String>();
        scrollObject.put("element", elementID);
        scrollObject.put("predicateString", "label == 'Privacy' AND name == 'Privacy' AND type == 'XCUIElementTypeCell'");
        scrollObject.put("direction", "down");
        driver.executeScript("mobile:scroll", scrollObject);

        WebElement privacyBtn = driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"Privacy\"]"));
        privacyBtn.click();

        // Verification - Check the title is Privacy
        assertThat(isDisplayed(By.xpath("//XCUIElementTypeStaticText[@name=\"Privacy\"]"), 5)).as("Verify Privacy Setting is displayed").isTrue();
        // Only needed for the recording video :-)
        waiting(2);

        // 4. This is not a must - Since we didn't start our app from the capabilities,
        // We can close the app using the appium terminate app command
        driver.terminateApp("com.apple.Preferences");

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

    public static void waiting(int sec){
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
