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

public class SeleniumTest {
    public RemoteWebDriver driver;

    /**
     * A Test Watcher is needed to be able to get the results of a Test so that it can be sent to Sauce Labs
     * Note that the name is never actually used
     */
    @Rule
    public SauceTestWatcher watcher = new SauceTestWatcher();

    /**
     * TestName Rule allows dynamically sending name information to Sauce Labs
     */
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setup() throws MalformedURLException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("access_key", System.getenv("SAUCE_ACCESS_KEY"));
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
