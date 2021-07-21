package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Assert;
import org.junit.Test;

public class PerformanceTests extends SauceBaseTest {
    @Override
    public SauceOptions createSauceOptions() {
        return SauceOptions.chrome()
                .setExtendedDebugging()
                .setName(testName.getMethodName())
                .setCapturePerformance()
                .build();
    }

    @Test
    public void performanceDidntDegrade() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();

        Assert.assertTrue(loginPage.getPageLoadTime() < 2000);
    }
}
