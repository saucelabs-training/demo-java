package com.saucedemo.tests;

import com.saucedemo.WebTestsBase;
import com.saucedemo.pages.LoginPage;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.RemoteWebDriver;

public class PerformanceTests extends WebTestsBase {

    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    private SauceSession session;

    @After
    public void tearDown() {
        session.stop(true);
    }

    @Test
    public void performanceDidntDegrade() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setExtendedDebugging(true);
        sauceOptions.setCapturePerformance(true);
        sauceOptions.setName(testName.getMethodName());
        sauceOptions.setName("simplePerformanceTest");
        session = new SauceSession(sauceOptions);

        RemoteWebDriver driver = session.start();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();

        Assert.assertTrue(loginPage.getPageLoadTime() < 2000);
    }


}
