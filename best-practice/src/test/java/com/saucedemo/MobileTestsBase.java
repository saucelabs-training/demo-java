package com.saucedemo;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

/**
 * Mobile Tests extend this class to ensure the correct driver.
 */
public abstract class MobileTestsBase extends AbstractTestBase {
    /**
     * This casts RemoteWebDriver to AppiumDriver for mobile tests.
     *
     * @return instance of Appium Driver
     */
    @SuppressWarnings("unchecked")
    public AppiumDriver getDriver() {
        return (AppiumDriver) driver;
    }
}
