package com.realdevice.unifiedplatform;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class RDCTestStatusUpdateTest {
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
        capabilities.setCapability("name", name.getMethodName());

        capabilities.setCapability("app", "storage:filename=" +
                "iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.0.ipa");

        driver = new IOSDriver(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
    }


    @After
    public void tearDown() {
        if (getDriver() != null) {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
            getDriver().quit();
        }
    }

    @Test
    public void openAppAndSetTestStatus() {
        assertTrue(getDriver().findElement(By.id("test-LOGIN")).isDisplayed());
    }
}
