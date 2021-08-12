package com.saucedemo;

import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractTestBase {
    public final static String buildName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    protected final static String sauceUsername = System.getenv("SAUCE_USERNAME");
    protected final static String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    protected final static String screenerApiKey = System.getenv("SCREENER_API_KEY");
    protected RemoteWebDriver driver;

    public class SauceTestWatcher extends TestWatcher {
        @Override
        protected void succeeded(Description description) {
            if (driver != null) {
                driver.executeScript("sauce:job-result=passed");
                driver.quit();
            }
        }

        @Override
        protected void failed(Throwable e, Description description) {
            if (driver != null) {
                driver.executeScript("sauce:job-result=failed");
                driver.quit();
            }
        }
    }
}
