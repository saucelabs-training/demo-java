package com.saucedemo.selenium.accessibility;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DequeAxeExampleTest {
    public RemoteWebDriver driver;

    @Rule
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    @Before
    public void setup() throws MalformedURLException {
        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("name", name.getMethodName());
        sauceOpts.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOpts.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("sauce:options", sauceOpts);

        String sauceUrl = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
        driver = new RemoteWebDriver(new URL(sauceUrl), chromeOptions);
    }

    @Test
    public void accessibilityTest() {
        driver.navigate().to("https://www.saucedemo.com");
        AxeBuilder axeBuilder = new AxeBuilder();
        Results accessibilityResults = axeBuilder.analyze(driver);
        Assert.assertEquals(3, accessibilityResults.getViolations().size());
    }

    private class SauceTestWatcher extends TestWatcher {
        @Override
        protected void failed(Throwable e, Description description) {
            driver.executeScript("sauce:job-result=failed");
        }

        @Override
        protected void succeeded(Description description) {
            driver.executeScript("sauce:job-result=passed");
        }

        @Override
        protected void finished(Description description) {
            driver.quit();
        }
    }
}
