package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.Endpoints;
import com.saucedemo.TestBase;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RealDeviceWebTests extends TestBase {
    @Parameterized.Parameter
    public String deviceName;
    private AppiumDriver<MobileElement> driver;

    @Parameterized.Parameters()
    public static Collection<Object[]> iosConfigurations() {
        return Arrays.asList(new Object[][]{
                {"iPhone 11.*"},
                {"iPhone 12.*"},
                {"iPad 10.*"},
                {"iPad Air.*"},
                {"iPad.*"},
                // Duplication below for demo purposes of massive parallelization
        });
    }

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("language", "en");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("deviceName", deviceName);
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
