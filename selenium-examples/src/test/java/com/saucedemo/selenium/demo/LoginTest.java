package com.saucedemo.selenium.demo;

import com.saucedemo.selenium.TestBase;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @DisplayName("Swag Labs Login with Selenium")
  @Test
  public void swagLabsLoginTest() {
    driver.get("https://www.saucedemo.com");

    By usernameFieldLocator = By.cssSelector("#user-name");
    By passwordFieldLocator = By.cssSelector("#password");
    By submitButtonLocator = By.cssSelector(".btn_action");

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
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
