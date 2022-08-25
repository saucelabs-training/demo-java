package com.saucedemo.selenium.junit4.demo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * An example on how to run Java + JUnit 4 test in the Sauce Labs Desktop Web cloud Chrome Extensions
 * Chrome extensions can automatically be installed by Chrome when you initially start the browser.
 */
public class ChromeExtensionTest {
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

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setCapability("platformName", "Windows 10");
        browserOptions.setCapability("browserVersion", "latest");
        // Add Extension
        String chromeExtFile = "src/test/java/com/saucedemo/selenium/junit4/demo/ninja-saucebot.crx";
        File ext = new File(chromeExtFile);
        browserOptions.addExtensions(ext);

        browserOptions.setCapability("sauce:options", sauceOptions);
        URL url = new URL("https://ondemand.eu-central-1.saucelabs.com/wd/hub");
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @Test
    public void chromeExtensionAddNinjaSaucebotImg() {
        driver.navigate().to("https://www.saucedemo.com");

        // Verification
        Assert.assertTrue(isNinjaSaucebotExist());
    }

    public boolean isNinjaSaucebotExist() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bot_column2")));
        } catch (TimeoutException e){
            return false;
        }
        return true;
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
