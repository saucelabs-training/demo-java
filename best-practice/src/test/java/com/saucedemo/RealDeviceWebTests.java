package com.saucedemo;

import com.pages.LoginPage;
import com.pages.ProductsPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;

import static org.junit.Assert.assertTrue;

public class RealDeviceWebTests {
    private AppiumDriver<MobileElement> driver;

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

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
        capabilities.setCapability("name", name.getMethodName());

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
}
