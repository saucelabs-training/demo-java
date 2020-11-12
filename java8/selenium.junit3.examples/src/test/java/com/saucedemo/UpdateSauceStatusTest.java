package com.saucedemo;

import junit.framework.TestCase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class UpdateSauceStatusTest extends TestCase {
    private WebDriver driver;
    private boolean isTestPassed = false;

    //without sauce bindings
    public void setUp() throws MalformedURLException {
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accessKey);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY,  chromeOpts);
        caps.setCapability("sauce:options", sauceOpts);
        caps.setCapability("browserName", "googlechrome");
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("platformName", "windows 10");

        String sauceUrl = "https://ondemand.saucelabs.com:443/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, caps);
    }
    public void tearDown() {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + isTestPassed);
        driver.quit();
    }
    public void testSauceStatus() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus2() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus3() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus4() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus5() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus6() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus7() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus8() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus9() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
    public void testSauceStatus10() {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        assertEquals(getTitle, "Swag Labs");
        isTestPassed = true;
    }
}
