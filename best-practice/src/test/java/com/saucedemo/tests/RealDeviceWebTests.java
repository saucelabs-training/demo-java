package com.saucedemo.tests;

import com.pages.LoginPage;
import com.pages.ProductsPage;
import com.saucedemo.Endpoints;
import com.saucedemo.TestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;

import java.net.MalformedURLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RealDeviceWebTests extends TestBase {
    private AppiumDriver<MobileElement> driver;
    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("language", "en");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", "iPhone 11.*");
        /*
        * if you set the browserName => always starts with webcontext
            if you set the app => always starts with native context
            if you set the package/bundleId => always starts with native context
            if you have a hybrid app and set autoWebview  => always starts with webview
        * */
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("name", testName.getMethodName());
        capabilities.setCapability("build", buildName);

        driver = new IOSDriver(Endpoints.getRealDevicesHub(), capabilities);
        resultReportingTestWatcher.setDriver(driver);
    }
    @Test
    public void loginWorks() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        loginPage.login("standard_user");
        assertTrue(new ProductsPage(driver).isDisplayed());
    }
    @Test(expected = TimeoutException.class)
    public void lockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("locked_out_user");
        assertFalse(new ProductsPage(driver).isDisplayed());
    }
    @Test(expected = TimeoutException.class)
    public void invalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("foo_bar_user");
        assertFalse(new ProductsPage(driver).isDisplayed());
    }
}
