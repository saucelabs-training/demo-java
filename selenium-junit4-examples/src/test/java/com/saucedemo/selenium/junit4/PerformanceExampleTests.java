package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class PerformanceExampleTests {

    private SauceSession session;
    private SauceOptions sauceOptions;
    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    @After
    public void tearDown() {
        session.stop(true);
    }

    @Test
    public void simplePerformanceTest() {
        //This is the easiest way to run a performance test in Sauce
        //Let Sauce do all the work and keep track of the baseline
        //and the regressions
        sauceOptions = new SauceOptions();
        sauceOptions.setExtendedDebugging(true);
        sauceOptions.setCapturePerformance(true);
        sauceOptions.setName(testName.getMethodName());

        session = new SauceSession(sauceOptions);
        WebDriver driver = session.start();
        driver.get("https://www.saucedemo.com");
    }

    @Test
    public void specificMetricsTests() {
        //If you want, you can assert on specific metrics such as load time, score...
        // This is a less reliable way to test performance
        sauceOptions = new SauceOptions();
        sauceOptions.setExtendedDebugging(true);
        sauceOptions.setCapturePerformance(true);
        sauceOptions.setName(testName.getMethodName());
        session = new SauceSession(sauceOptions);

        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");
        WebDriver driver = session.start();
        driver.get("https://www.saucedemo.com");

        Map<String, Object> perfMetrics = (Map<String, Object>) ((JavascriptExecutor)driver).executeScript("sauce:log", metrics);
        Integer loadTime = Integer.parseInt(perfMetrics.get("load").toString());
        Assert.assertTrue(loadTime < 1500);
    }

    @Test
    public void multiPagePerformanceExample() {
        //This kind of test is helpful when you need to check the performance of a page
        // after a long flow
        sauceOptions = new SauceOptions();
        sauceOptions.setExtendedDebugging(true);
        sauceOptions.setCapturePerformance(true);
        sauceOptions.setName(testName.getMethodName());
        session = new SauceSession(sauceOptions);

        WebDriver driver = session.start();
        //We disable sauce performance until we reach the desired page
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("sauce:performanceDisable");
        driver.get("https://www.saucedemo.com");

        //Enable performance check before navigating to the page we actually want to test
        js.executeScript("sauce:performanceEnable");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        //The end result is a test that will only capture the performance for https://www.saucedemo.com/inventory.html
        // And not for https://www.saucedemo.com
    }
}
