package com.examples.simple_example;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
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
public class AndroidNativeAppTest {

    By productsScreenLocator = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    By sortButtonLocator = AppiumBy
            .accessibilityId("Shows current sorting order and displays available sorting options");
    By sortModalLocator = By.id("com.saucelabs.mydemoapp.android:id/sortTV");

    @Rule
    public TestName name = new TestName();

    // This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Sauce Android Native App  - Before hook");
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

        // For all capabilities please check
        // https://appium.io/docs/en/2.0/guides/caps/
        // Use the platform configuration
        // https://saucelabs.com/platform/platform-configurator#/
        // to find the emulators/real devices names, OS versions and appium versions you
        // can use for your testings

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        if (rdc.equals("true")) {
            // capabilities.setCapability("appium:deviceName", "Samsung.*");
            sauceOptions.setCapability("resigningEnabled", true);
            /*
             * Deprecated way? Not what is in the docs
             * sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
             */
            sauceOptions.setCapability("appiumVersion", "latest");
            sauceOptions.setCapability("networkCapture", true);
        } else {
            capabilities.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
            capabilities.setCapability("appium:appWaitActivity", ".view.activities.MainActivity");
            sauceOptions.setCapability("appiumVersion", "latest");
        }
        /* UNCOMMENT THIS LINE TO FORCE FAIL */
        // capabilities.setCapability("appium:platformVersion", "10");

        String appName = "mda-2.1.0-24.apk";
        capabilities.setCapability("appium:app", "storage:filename=" + appName);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());

        sauceOptions.setCapability("build", "feature/demo-rdc");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            driver = new AndroidDriver(url, capabilities);
        } catch (Exception e) {
            System.out.println("Error to create Android Driver: " + e.getMessage());
            return;
        }

        System.out.println("Job ID is: " + driver.getCapabilities().getCapability("appium:jobUuid"));
        // Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void verifyPromptSortModal() throws MalformedURLException {
        // Wait for the application to start and load the initial screen (products
        // screen)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsScreenLocator));

        driver.executeScript("sauce:context=Finding sort button element");
        driver.findElement(sortButtonLocator).click();

        // Verify the sort modal is displayed on screen
        assertThat(isDisplayed(sortModalLocator, 5)).as("Verify sort modal is displayed").isTrue();
    }

    @Test
    public void testFailsForSpecificAndroidVersion() throws MalformedURLException {
        // Fail the test if Android version is 10
        String platformVersion = driver.getCapabilities().getCapability("appium:platformVersion").toString();
        System.out.println("Platform version: " + platformVersion);

        if (platformVersion.equals("10")) {
            System.out.println("Failing the test due to Android version 10.");
            assertThat(false).as("Failing this test because it's running on Android 10").isTrue();
            driver.executeScript("sauce:job-result=failed");
        } else {
            System.out.println("Test passed because it's not running on Android 10.");
            assertThat(true).as("Test passed").isTrue();
            driver.executeScript("sauce:job-result=passed");
        }
    }

    private Boolean isDisplayed(By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

}
