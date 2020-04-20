package com.yourcompany.Tests;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class TestNGBase {

    protected static ThreadLocal<SauceSession> sessionThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<SauceOptions> optionsThreadLocal = new ThreadLocal<>();

    public SauceSession getSession() {
        return sessionThreadLocal.get();
    }

    public SauceOptions getOptions() {
        return optionsThreadLocal.get();
    }

    public WebDriver getDriver() {
        return getSession().getDriver();
    }

    public void createOptions(Method method) {
        optionsThreadLocal.set(new SauceOptions());
        optionsThreadLocal.get().setName(method.getName());
    }

    public SauceOptions updateOptions(SauceOptions options) {
        return options;
    }

    @BeforeMethod
    public void setup (Method method) {
        createOptions(method);
        SauceOptions options = updateOptions(getOptions());
        sessionThreadLocal.set(new SauceSession(options));

        sessionThreadLocal.get().start();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        getSession().stop(result.isSuccess());
    }

    @AfterClass
    void terminate () {
        sessionThreadLocal.remove();
    }
}
