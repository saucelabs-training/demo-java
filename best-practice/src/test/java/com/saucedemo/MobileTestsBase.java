package com.saucedemo;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public abstract class MobileTestsBase extends AbstractTestBase {
    /**
     * This casts RemoteWebDriver to AppiumDriver for mobile tests.
     *
     * @return instance of Appium Driver
     */
    @SuppressWarnings("unchecked")
    public AppiumDriver<WebElement> getDriver() {
        return (AppiumDriver<WebElement>) driver;
    }
}
