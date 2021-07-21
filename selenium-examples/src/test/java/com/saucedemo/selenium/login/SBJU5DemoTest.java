package com.saucedemo.selenium.login;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SBJU5DemoTest extends SauceBaseTest {

    @DisplayName("Swag Labs Login")
    @Test
    public void swagLabsLoginTest() {
        driver.get("https://www.saucedemo.com");

        By usernameFieldLocator = By.id("user-name");
        By passwordFieldLocator = By.id("password");
        By submitButtonLocator = By.id("login-button");

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until((driver) -> driver.findElement(usernameFieldLocator).isDisplayed());

        WebElement userNameField = driver.findElement(usernameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement submitButton = driver.findElement(submitButtonLocator);

        userNameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        submitButton.click();

        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }
}
