package com.saucedemo;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
    //We need to create a ThreadLocal object to be accessed by a specific thread
    private ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public WebDriver getDriver(){
        return threadLocalDriver.get();
    }

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {
        String sauceUsername = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setCapability(CapabilityType.PLATFORM_NAME, "windows 10");
        chromeOpts.setCapability(CapabilityType.BROWSER_VERSION, "latest");

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", sauceUsername);
        sauceOpts.setCapability("accessKey", sauceAccessKey);
        sauceOpts.setCapability("name", method.getName());
        sauceOpts.setCapability("build", "best-practices");
        sauceOpts.setCapability("tags", "['best-practices', 'best-practices']");

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability(ChromeOptions.CAPABILITY,  chromeOpts);
        capabilities.setCapability("sauce:options", sauceOpts);

        String sauceUrl = "https://ondemand.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        threadLocalDriver.set(new RemoteWebDriver(url, capabilities));
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getDriver().quit();
    }
}

