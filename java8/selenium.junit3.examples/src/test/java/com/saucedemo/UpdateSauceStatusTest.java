package com.saucedemo;

import com.saucelabs.saucebindings.SauceSession;
import junit.framework.TestCase;
import org.openqa.selenium.WebDriver;

public class UpdateSauceStatusTest extends TestCase {
    private WebDriver driver;
    private boolean isTestPassed = false;
    private SauceSession session;

    //with sauce bindings
    public void setUp() {
        session = new SauceSession();
        driver = session.start();
    }

    public void tearDown() {
        session.stop(isTestPassed);
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
