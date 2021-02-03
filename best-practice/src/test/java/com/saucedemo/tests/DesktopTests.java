package com.saucedemo.tests;

import com.pages.LoginPage;
import com.pages.ProductsPage;
import com.saucedemo.WebTestsBase;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DesktopTests extends WebTestsBase {
    @Before
    public void setUp() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setName(testName.getMethodName());

        SauceSession session = new SauceSession(sauceOptions);
        driver = session.start();
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void loginWorks() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("standard_user");
        assertTrue(new ProductsPage(driver).isDisplayed());
    }
}
