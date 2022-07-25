package com.realdevices;

import io.appium.java_client.AppiumDriver;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SauceTestWatcher extends TestWatcher {
    private RemoteWebDriver driver;

    public void setDriver(AppiumDriver driver)
    {
        this.driver = driver;
    }

    @Override
    protected void succeeded(Description description) {
        if(driver != null)
        {
            System.out.println("Test Passed!");
            driver.executeScript("sauce:job-result=passed");
            driver.quit();
        }
    }

    @Override
    public void failed(Throwable e, Description description) {
        if(driver != null)
        {
            System.out.println("Test Failed!");
            driver.executeScript("sauce:job-result=failed");
            driver.quit();
        }
    }
}
