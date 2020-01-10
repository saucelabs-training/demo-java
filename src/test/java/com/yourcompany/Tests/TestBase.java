package com.yourcompany.Tests;

import com.saucelabs.simplesauce.Browser;
import com.saucelabs.simplesauce.SauceOptions;
import com.saucelabs.simplesauce.SaucePlatform;
import com.saucelabs.simplesauce.SauceSession;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class TestBase  {

    protected static ThreadLocal<SauceSession> session = new ThreadLocal<>();
    protected static ThreadLocal<SauceOptions> options = new ThreadLocal<>();

    public SauceSession getSession() {
        return session.get();
    }

    public WebDriver getDriver() {
        return getSession().getDriver();
    }

    @BeforeMethod
    public void setup (Method method) {
        options.set(new SauceOptions());
        options.get().setName(method.getName());

        if (System.getenv("START_TIME") != null) {
            options.get().setBuild("Build Time: " + System.getenv("START_TIME"));
        }

        String platform;
        if (System.getProperty("platform") != null) {
            platform = System.getProperty("platform");
        } else {
            platform = "default";
        }

        switch(platform) {
            case "windows_10_edge":
                options.get().setPlatformName(SaucePlatform.WINDOWS_10);
                options.get().setBrowserName(Browser.EDGE);
                break;
            case "mac_sierra_chrome":
                options.get().setPlatformName(SaucePlatform.MAC_SIERRA);
                options.get().setBrowserName(Browser.CHROME);
                break;
            case "windows_8_ff":
                options.get().setPlatformName(SaucePlatform.WINDOWS_8);
                options.get().setBrowserName(Browser.FIREFOX);
                break;
            case "windows_8_1_ie":
                options.get().setPlatformName(SaucePlatform.WINDOWS_8_1);
                options.get().setBrowserName(Browser.INTERNET_EXPLORER);
                break;
            case "mac_mojave_safari":
                options.get().setPlatformName(SaucePlatform.MAC_MOJAVE);
                options.get().setBrowserName(Browser.SAFARI);
                break;
            default:
                // accept Sauce defaults
                break;
        }

        session.set(new SauceSession(options.get()));

        getSession().start();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        getSession().stop(result.isSuccess());
    }

    @AfterClass
    void terminate () {
        session.remove();
    }
}
