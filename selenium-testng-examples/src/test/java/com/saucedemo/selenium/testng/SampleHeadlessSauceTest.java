package com.saucedemo.selenium.testng;

import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SampleHeadlessSauceTest {
    private WebDriver driver;
    private SauceSession sauceSession;

    @Test
    public void main()  {

        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setPlatformName(SaucePlatform.LINUX);
        sauceOptions.setName("headless-chrome-test-java");
        sauceOptions.setBuild("Sample Headless Test");

        sauceSession = new SauceSession(sauceOptions);
        sauceSession.setDataCenter(DataCenter.US_EAST);
        driver = sauceSession.start();

        driver.get("https://www.saucedemo.com");
        System.out.println("title of page is: " + driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }

    /* Sends results to SauceLabs.com */
    @AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        sauceSession.stop(true);
    }
}
