package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.WebTestsBase;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.TimeoutException;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class DesktopTests extends WebTestsBase {
    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public String browserName;
    @Parameterized.Parameter(1)
    public String browserVersion;
    @Parameterized.Parameter(2)
    public String platform;

    @Parameterized.Parameters()
    public static Collection<Object[]> crossBrowserData() {
        return Arrays.asList(new Object[][]{
                {"chrome", "latest", "Windows 10"},
                {"chrome", "latest-1", "Windows 10"},
                {"safari", "latest", "macOS 10.14"},
                {"chrome", "latest", "macOS 10.14"},
                // Duplication below for demo purposes of massive parallelization
                {"chrome", "latest", "Windows 10"},
                {"chrome", "latest-1", "Windows 10"},
                {"safari", "latest", "macOS 10.14"},
                {"chrome", "latest", "macOS 10.14"},
                {"chrome", "latest", "Windows 10"},
                {"chrome", "latest-1", "Windows 10"},
                {"safari", "latest", "macOS 10.14"},
                {"chrome", "latest", "macOS 10.14"},
                {"chrome", "latest", "Windows 10"},
                {"chrome", "latest-1", "Windows 10"},
                {"safari", "latest", "macOS 10.14"},
                {"chrome", "latest", "macOS 10.14"},
                {"chrome", "latest", "Windows 10"},
                {"chrome", "latest-1", "Windows 10"},
                {"safari", "latest", "macOS 10.14"},
                {"chrome", "latest", "macOS 10.14"},
                {"chrome", "latest", "Windows 10"},
                {"chrome", "latest-1", "Windows 10"},
                {"safari", "latest", "macOS 10.14"},
                {"chrome", "latest", "macOS 10.14"},
        });
    }

    @Before
    public void setUp() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setCapability("browserName", browserName);
        sauceOptions.setCapability("browserVersion", browserVersion);
        sauceOptions.setCapability("platformName", platform);
        sauceOptions.setName(testName.getMethodName());
        sauceOptions.setBuild(buildName);

        SauceSession session = new SauceSession(sauceOptions);
        driver = session.start();
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test()
    public void loginWorks() {
        LoginPage loginPage = new LoginPage(driver);
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
