package com.saucedemo;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CheckoutTest extends TestBase {
  @Test
  @DisplayName("Enter incorrect checkout information")
  public void badInfo() {
    login();
    addItemToCart();
    driver.findElement(AppiumBy.accessibilityId("View cart")).click();
    driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout")).click();

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/checkoutSV"));
    driver.findElement(AppiumBy.accessibilityId("Saves user info for checkout")).click();

    Assertions.assertEquals(
        "Please provide your full name.",
        driver
            .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameErrorTV"))
            .getText(),
        "Expected error not found on page");
  }

  @Test
  @DisplayName("Enter correct checkout information")
  public void goodInfo() {
    login();
    addItemToCart();
    driver.findElement(AppiumBy.accessibilityId("View cart")).click();
    driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout")).click();
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameET"))
        .sendKeys("Luke Perry");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ET"))
        .sendKeys("123 Main St");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityET"))
        .sendKeys("Beverly Hills");
    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/checkoutSV"));
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipET")).sendKeys("90210");
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryET")).sendKeys("USA");

    driver.findElement(AppiumBy.accessibilityId("Saves user info for checkout")).click();

    Assertions.assertTrue(
        driver
            .findElement(
                AppiumBy.accessibilityId(
                    "Saves payment info and launches screen to review checkout data"))
            .isDisplayed(),
        "Information Submission Unsuccessful");
  }

  @Test
  @DisplayName("Enter payment information")
  public void enterPayment() {
    login();
    addItemToCart();
    driver.findElement(AppiumBy.accessibilityId("View cart")).click();

    driver.findElement(AppiumBy.accessibilityId("Confirms products for checkout")).click();
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameET"))
        .sendKeys("Luke Perry");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ET"))
        .sendKeys("123 Main St");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityET"))
        .sendKeys("Beverly Hills");
    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/checkoutSV"));
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipET")).sendKeys("90210");
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryET")).sendKeys("USA");

    driver.findElement(AppiumBy.accessibilityId("Saves user info for checkout")).click();
    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/checkoutSV"));
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))
        .sendKeys("Luke Perry");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cardNumberET"))
        .sendKeys("1234");

    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/expirationDateET"))
        .sendKeys("01/30");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/securityCodeET"))
        .sendKeys("123");

    driver
        .findElement(
            AppiumBy.accessibilityId(
                "Saves payment info and launches screen to review checkout data"))
        .click();

    Assertions.assertTrue(
        driver
            .findElement(AppiumBy.accessibilityId("Completes the process of checkout"))
            .isDisplayed(),
        "Payment Submission Unsuccessful");
  }

  private void login() {
    driver.findElement(AppiumBy.accessibilityId("View menu")).click();

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));
    driver.findElement(AppiumBy.accessibilityId("Login Menu Item")).click();

    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"))
        .sendKeys("standard_user");
    driver
        .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET"))
        .sendKeys("secret_sauce");
    driver.findElement(AppiumBy.accessibilityId("Tap to login with given credentials")).click();
  }

  private void addItemToCart() {
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productIV")).click();

    scrollDown(AppiumBy.accessibilityId("Container for fragments"));
    driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart")).click();
  }
}
