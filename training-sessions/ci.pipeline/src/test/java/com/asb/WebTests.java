package com.asb;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

public class WebTests {

    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    @Test
    public void shouldOpenChrome() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setName(testName.getMethodName());

        SauceSession session = new SauceSession(sauceOptions);
        WebDriver driver = session.start();

        //navigate to the url of the Sauce Labs Sample app
        driver.navigate().to("https://www.saucedemo.com");

        //Create an instance of a Selenium explicit wait
        // so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //wait for the user name field to be visible and store that element into a variable
        By userNameFieldLocator = By.cssSelector("[type='text']");
        WebElement userNameField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator));
        assertTrue(userNameField.isDisplayed());
    }
}
