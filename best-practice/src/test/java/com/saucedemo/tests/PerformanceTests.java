package com.saucedemo.tests;

import com.saucedemo.AbstractTestBase;
import com.saucedemo.pages.LoginPage;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Performance Tests.
 */
public class PerformanceTests extends AbstractTestBase {
    @Before
    public void setup() {
        SauceOptions sauceOptions = SauceOptions.chrome()
                .setExtendedDebugging()
                .setName(testName.getMethodName())
                .setCapturePerformance()
                .build();
        sauceOptions.sauce().setBuild(buildName);

        driver = new SauceSession(sauceOptions).start();
    }

    @Test
    public void performanceDidNotDegrade() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();

        Assert.assertTrue(loginPage.getPageLoadTime() < 2000);
    }
}
