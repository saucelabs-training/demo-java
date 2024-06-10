package com.examples.network_throttling;

import com.google.common.collect.ImmutableMap;
import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.ios.IOSDriver;
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

import static com.helpers.Constants.SAUCE_EU_URL;
import static com.helpers.Constants.SAUCE_US_URL;
import static com.helpers.Constants.region;

public class NetworkThrottlingIosRDCTest {

    private final static By moreTabButton = By.xpath("//XCUIElementTypeButton[@name=\"More-tab-item\"]");
    private final static By webviewButton = By.xpath("//XCUIElementTypeButton[@name=\"Webview-menu-item\"]");
    private final static By urlInput = By.xpath("//XCUIElementTypeTextField[@value=\"https://www.website.com\"]");
    private final static By goButton = By.xpath("//XCUIElementTypeButton[@name=\"Go To Site\"]");
    private final static By moreInfoLink = By.xpath("//XCUIElementTypeLink[@name=\"Show more info\"]");

    @Rule
    public TestName name = new TestName();

    // This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private IOSDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("deviceName", ".*");

        // Sauce capabilities
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        String appName = "SauceLabs-Demo-App.ipa";

        String testName = name.getMethodName();
        switch (testName) {
            case "regularNetworkSpeedTest":
                System.out.println("Running regular network speed test");
                capabilities.setCapability("appium:app", "storage:filename=" + appName);
                sauceOptions.setCapability("name", "Regular network speed test");
                break;
            case "throttledNetworkProfileTest":
                System.out.println("Running throttled 2G Slow test");
                capabilities.setCapability("appium:app", "storage:filename=" + appName);
                sauceOptions.setCapability("name", "Throttled 2G Slow test");
                // Set a predefined network profile
                sauceOptions.setCapability("networkProfile", "2G");
                break;
            case "throttledCustomNetworkConditionsTest":
                System.out.println("Running throttled custom network conditions test");
                capabilities.setCapability("appium:app", "storage:filename=" + appName);
                sauceOptions.setCapability("name", "Throttled custom network conditions test");
                // Set custom network conditions
                sauceOptions.setCapability("networkConditions", ImmutableMap.of(
                        "downloadSpeed", 5000,
                        "uploadSpeed", 3000,
                        "latency", 200,
                        "loss", 5
                ));
                break;
            case "throttledNetworkSpeedWebTest":
                System.out.println("Running throttled 2G Slow web test");
                capabilities.setCapability("browserName", "Safari");
                sauceOptions.setCapability("name", "Throttled 2G Slow web test");
                // Set a predefined network profile
                sauceOptions.setCapability("networkProfile", "2G");
                break;
            default:
                System.out.println("No test configuration found for " + testName);
                break;
        }

        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            driver = new IOSDriver(getAppiumUrl(), capabilities);
        } catch (Exception e) {
            System.out.println("Error to create the iOS Driver: " + e.getMessage());
            throw e;
        }

        // Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void regularNetworkSpeedTest() {
        testNetworkConditions();
    }

    @Test
    public void throttledNetworkProfileTest() {
        testNetworkConditions();
    }

    @Test
    public void throttledCustomNetworkConditionsTest() {
        testNetworkConditions();
    }

    @Test
    public void throttledNetworkSpeedWebTest() {
        driver.get("https://www.fast.com");
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("show-more-details-link")));
    }

    private void testNetworkConditions() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.presenceOfElementLocated(moreTabButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(webviewButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(urlInput)).sendKeys("fast.com");
        wait.until(ExpectedConditions.presenceOfElementLocated(goButton)).click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(moreInfoLink));
    }

    private URL getAppiumUrl() throws MalformedURLException {
        switch (region) {
            case "us":
                return new URL(SAUCE_US_URL);
            case "eu":
            default:
                return new URL(SAUCE_EU_URL);
        }
    }
}
