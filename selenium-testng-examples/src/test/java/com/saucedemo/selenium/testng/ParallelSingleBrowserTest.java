package com.saucedemo.selenium.testng;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParallelSingleBrowserTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<SauceSession> session = new ThreadLocal<>();

    private static String buildName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    @BeforeMethod
    public void setup(Method method) {
        SauceOptions options = new SauceOptions();
        options.setName(method.getName());
        options.setBuild(buildName);

        session.set(new SauceSession(options));
        driver.set(session.get().start());
    }

    @Test
    public void testCase1() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase2() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase3() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase4() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase5() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase6() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase7() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase8() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase9() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test
    public void testCase10() {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        session.get().stop(result.isSuccess());
    }
}
