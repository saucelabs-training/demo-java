package com.saucedemo;

import com.pages.LoginPage;
import com.pages.ProductsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

import static org.junit.Assert.assertTrue;

public class EmusimWebAppTests extends WebTestsBase {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    @Before
    public void setUp() throws MalformedURLException {
        //Configure these using Platform Configurator:
        // https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "13.4");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("name", name.getMethodName());

        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("newCommandTimeout", "90");
        //Emusim devices have Simulator/Emulator in the name
        capabilities.setCapability("deviceName", "iPhone XS Max Simulator");

        driver = new RemoteWebDriver(Endpoints.getEmusimHub(),
                capabilities);
    }

    @After
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    @Test
    public void loginWorks() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("standard_user");
        assertTrue(new ProductsPage(driver).isDisplayed());
    }
}
