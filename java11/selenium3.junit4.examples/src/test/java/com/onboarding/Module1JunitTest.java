package com.onboarding;

import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;


public class Module1JunitTest {
    private WebDriver driver;
    private SauceSession session;

    @Test
    public void shouldOpenSafari() {
        /**
         * Using Java 9+, we get a new and simple way to start a session
         * in Sauce Labs using Sauce Bindings
         * https://opensource.saucelabs.com/sauce_bindings/docs/getting-started
         */
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setPlatformName(SaucePlatform.MAC_HIGH_SIERRA);
        sauceOptions.setBrowserName(Browser.SAFARI);
        //set the build name of the application
        sauceOptions.setBuild("Onboarding Sample App - Java-Junit4");
        //set your test case name so that it shows up in Sauce Labs
        sauceOptions.setName("1-first-test");

        session = new SauceSession(sauceOptions);
        driver = session.start();

        //navigate to the url of the Sauce Labs Sample app
        driver.navigate().to("https://www.saucedemo.com");

        //Create an instance of a Selenium explicit wait
        // so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //wait for the user name field to be visible and store that element into a variable
        By userNameFieldLocator = By.cssSelector("[type='text']");
        WebElement userNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator));
        Boolean result = userNameField != null;
        session.stop(result);
        assertTrue(result);
    }
}
