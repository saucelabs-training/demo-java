package com.saucedemo.selenium.testng.demo;

import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Example Tests for running with Sauce Bindings diriectly without test runner jar.
 */
public class SauceBindingsTest {
    protected SauceSession session;
    protected RemoteWebDriver driver;

    @BeforeMethod
    public void setup(Method method) {
        SauceOptions options = SauceOptions.chrome().setName(method.getName()).build();
        session = new SauceSession(options);
        driver = session.start();
    }

    @Test
    public void correctTitle() {
        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", driver.getTitle());
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        session.stop(result.isSuccess());
    }
}
