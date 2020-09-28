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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class Module4JunitTest {

    @Test
    public void bestPracticesTest() {
        /**
         * in this exercise we set additional capabilities below that align with
         * testing best practices such as tags, timeouts, and build name/numbers.
         *
         * Tags are an excellent way to control and filter your test automation
         * in Sauce Analytics. Get a better view into your test automation.
         */

        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setPlatformName(SaucePlatform.MAC_HIGH_SIERRA);
        sauceOptions.setBrowserName(Browser.SAFARI);
        //set the build name of the application
        sauceOptions.setBuild("Onboarding Sample App - Java-Junit4");
        //set your test case name so that it shows up in Sauce Labs
        sauceOptions.setName("4-best-practices");
        List<String> tags = Arrays.asList("sauceDemo", "demoTest", "module4", "javaTest");
        sauceOptions.setTags(tags);

        /** Another of the most important things that you can do to get started
         * is to set timeout capabilities for Sauce based on your organizations needs.
         * For example:
         * How long is the whole test allowed to run?*/

        // A test should never run longer than a few minutes even if it's possible
        // 3B+ tests analyzed showed that there is a direct correlation bw
        // test duration and failure rate
        sauceOptions.setMaxDuration(3600);
        /** A Selenium crash might cause a session to hang indefinitely.
         * Below is the max time allowed to wait for a Selenium command*/
        sauceOptions.setCommandTimeout(600);
        /** How long can the browser wait for a new command */
        sauceOptions.setIdleTimeout(1000);

        SauceSession session = new SauceSession(sauceOptions);
        WebDriver driver = session.start();

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
