package com.saucedemo.selenium.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.AppiumDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Demo tests with Appium.
 */
public class AppiumTest {
    public AppiumDriver driver;

    /**
     * A Test Watcher is needed to be able to get the results of a Test so that it can be sent to Sauce Labs.
     * Note that the name is never actually used
     */
    @RegisterExtension
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @BeforeEach
    public void setup(TestInfo testInfo) throws MalformedURLException {
        String platformName = System.getenv().getOrDefault("PLATFORM_NAME", "Android");
        MutableCapabilities options = new MutableCapabilities();
        options.setCapability("browserName", System.getenv().getOrDefault("BROWSER_NAME", null));
        options.setCapability("platformName", platformName);
        options.setCapability("appium:platformVersion", System.getenv("PLATFORM_VERSION"));
        //options.setCapability("browserVersion", System.getenv().getOrDefault("BROWSER_VERSION", null));
        options.setCapability("appium:deviceName", System.getenv().getOrDefault("DEVICE_NAME", "Google.*"));
        options.setCapability("appium:automationName", System.getenv().getOrDefault("AUTOMATION_NAME", "UiAutomator2"));

        ArrayList<String> tags = new ArrayList();
        if (System.getenv("GITPOD_WORKSPACE_ID") != null) {
            tags.add("gitpod");
        }

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("appiumVersion", "2.0.0");
        sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.put("build", System.getenv("BUILD"));
        sauceOptions.put("name", testInfo.getDisplayName());
        sauceOptions.put("tags", tags);
        options.setCapability("sauce:options", sauceOptions);

        String region = System.getenv().getOrDefault("REGION", "us-west-1");
        String ondemandUrl = "https://ondemand." + region + ".saucelabs.com:443/wd/hub";
        URL url = new URL(ondemandUrl);

        boolean isIos = platformName.toLowerCase() == "ios";
        if (isIos) {
            driver = new IOSDriver(url, options);
        } else {
            driver = new AndroidDriver(url, options);
        }
    }

    @DisplayName("Appium Test from Gitpod")
    @Test
    public void navigateAndClose() throws InterruptedException {
        // Add tests and assertions here
        Thread.sleep(5000);
    }

    /**
     * Custom TestWatcher for Sauce Labs projects.
     */
    public class SauceTestWatcher implements TestWatcher {
        @Override
        public void testSuccessful(ExtensionContext context) {
            driver.executeScript("sauce:job-result=passed");
            driver.quit();
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            driver.executeScript("sauce:job-result=failed");
            driver.quit();
        }
    }
}
