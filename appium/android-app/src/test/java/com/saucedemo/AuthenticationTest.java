package com.saucedemo;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class AuthenticationTest extends TestBase {

  @Test
  @DisplayName("Bad Login to Swag Labs")
  public void signInUnsuccessful() {
    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    // driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawerMenu"));

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));
    driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();

    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))
        .sendKeys("alice@example.com");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET"))
        .sendKeys("secret_sauce");
    driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

    WebElement errorElement =
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV"));

    Assertions.assertEquals("Sorry this user has been locked out.", errorElement.getText(),
        "Expected error not displayed");
  }

  @Test
  @DisplayName("Good Login to Swag Labs")
  public void signInSuccessful() {
    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    // driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawerMenu"));

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));
    driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();

    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))
        .sendKeys("standard_user");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET"))
        .sendKeys("secret_sauce");
    driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));
    Assertions.assertTrue(
        driver.findElement(AppiumBy.accessibilityId("Logout Menu Item")).isDisplayed());
  }

  @Test
  @DisplayName("Log Out of Swag Labs")
  public void logout() throws InterruptedException {
    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    // driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawerMenu"));

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));
    driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();

    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))
        .sendKeys("standard_user");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET"))
        .sendKeys("secret_sauce");

    driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();

    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));

    driver.findElement(AppiumBy.accessibilityId("Logout Menu Item")).click();
    driver.findElement(AppiumBy.id("android:id/button1")).click();

    WebElement loginButton =
        driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials"));
    Assertions.assertTrue(loginButton.isDisplayed(), "Login button is not displayed");
  }
}
