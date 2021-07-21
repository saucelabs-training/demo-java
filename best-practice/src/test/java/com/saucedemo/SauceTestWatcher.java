package com.saucedemo;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SauceTestWatcher extends TestWatcher {
    private RemoteWebDriver driver;

    public void setDriver(RemoteWebDriver driver) {
        this.driver = driver;
    }

    protected void succeeded(Description description) {
        if (driver != null) {
            driver.executeScript("sauce:job-result=passed");
            driver.quit();
        }
    }

    protected void failed(Description description) {
        if (driver != null) {
            driver.executeScript("sauce:job-result=failed");
            driver.quit();
        }
    }
}
