package com.saucedemo.selenium.junit4.demo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Tests for running Selenium tests directly with JUnit 4.
 */
public class SeleniumTest {
    public RemoteWebDriver driver;

    @Rule
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setup() throws MalformedURLException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.setCapability("name", testName.getMethodName());
        sauceOptions.setCapability("browserVersion", "latest");

        ChromeOptions options = new ChromeOptions();
        options.setCapability("sauce:options", sauceOptions);
        URL url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");

        driver = new RemoteWebDriver(url, options);
    }


    @Test
    public void correctTitle() {
        driver.navigate().to("https://www.saucedemo.com");
        assertEquals("Swag Labs", driver.getTitle());
    }

    /**
     * Custom TestWatcher for Sauce Labs projects.
     */
    protected class SauceTestWatcher extends TestWatcher {
        @Override
        protected void failed(Throwable e, Description description) {
            driver.executeScript("sauce:job-result=failed");
        }

        @Override
        protected void succeeded(Description description) {
            driver.executeScript("sauce:job-result=passed");
        }

        @Override
        protected void finished(Description description) {
            driver.quit();
        }
    }
}
