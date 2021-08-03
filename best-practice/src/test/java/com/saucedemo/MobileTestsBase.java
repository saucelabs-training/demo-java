package com.saucedemo;

import io.appium.java_client.AppiumDriver;

public class MobileTestsBase extends TestBase {
    public AppiumDriver getDriver() {
        return (AppiumDriver) driver;
    }
}
