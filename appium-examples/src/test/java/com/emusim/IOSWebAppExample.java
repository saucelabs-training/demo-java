package com.emusim;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertTrue;

public class IOSWebAppExample {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    private RemoteWebDriver driver;

    public RemoteWebDriver getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("appiumVersion", "1.17.1");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "13.2");
        capabilities.setCapability("deviceName", "iPhone XS Max Simulator");
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("name", name.getMethodName());

        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("newCommandTimeout", "90");

        driver = new RemoteWebDriver(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.saucelabs.com:443" + "/wd/hub"),
                capabilities);
    }

    @After
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }

    @Test
    public void shouldLoadWebsite() {
        getDriver().navigate().to("https://www.saucedemo.com");
        assertTrue(getDriver().findElement(By.id("user-name")).isDisplayed());
    }

    @Test
    public void shouldLogin() {
        getDriver().navigate().to("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.id("login-button")).click();
        assertTrue(getDriver().findElement(By.id("inventory_filter_container")).isDisplayed());
    }
}
