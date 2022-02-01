package com.saucedemo.tests;

import com.saucedemo.AbstractTestBase;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Desktop Tests. */
@RunWith(Parameterized.class)
public class FailureAnalysisTests extends AbstractTestBase {
    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public Browser browserName;
    @Parameterized.Parameter(1)
    public String browserVersion;
    @Parameterized.Parameter(2)
    public SaucePlatform platform;

    private static final int NUMBER_OF_TIMES_TO_EXECUTE = 100;

    @Parameterized.Parameters()
    public static Collection<Object[]> crossBrowserData() {
        Collection<Object[]> list = new ArrayList<>();
        for(int i = 0; i < NUMBER_OF_TIMES_TO_EXECUTE; i++) {
            list.add(new Object[] {
                    Browser.CHROME, "latest", SaucePlatform.WINDOWS_10 });
        }
        return list;
    }

    @Before
    public void setup() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setBrowserName(browserName);
        sauceOptions.setBrowserVersion(browserVersion);
        sauceOptions.setPlatformName(platform);
        sauceOptions.sauce().setName("loginWorks – failure");
        sauceOptions.sauce().setBuild(buildName);

        driver = new SauceSession(sauceOptions).start();
    }

    @Test()
    public void loginWorks() {
        long timestamp = System.currentTimeMillis() / 1000;

        if(timestamp % 2 == 0) {
            ((JavascriptExecutor) driver).executeScript("sauce:context=" + "Checking item for failure analysis");
            WebElement failure = driver.findElement(By.id("failure-analysis"));
            failure.sendKeys("test");
        }

        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("standard_user");
        assertTrue(new ProductsPage(driver).isDisplayed());
    }
}
