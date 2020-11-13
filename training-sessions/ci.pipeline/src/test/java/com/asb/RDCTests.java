package com.asb;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class RDCTests {
    private AppiumDriver<MobileElement> driver;

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("language", "en");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 11 Pro Max");
        /*
        * if you set the browserName => always starts with webcontext
            if you set the app => always starts with native context
            if you set the package/bundleId => always starts with native context
            if you have a hybrid app and set autoWebview  => always starts with webview
        * */
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("name", name.getMethodName());

        driver = new IOSDriver(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        resultReportingTestWatcher.setDriver(driver);
    }
    @Test
    public void webAppOpens() {
        getDriver().get("https://www.saucedemo.com");
        assertTrue(getDriver().findElement(By.cssSelector("[type='text']")).isDisplayed());
    }
}
