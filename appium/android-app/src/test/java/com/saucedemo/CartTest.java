package com.saucedemo;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartTest extends TestBase {
  @Test
  @DisplayName("Add product to cart")
  public void addToCart() {
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productIV")).click();

    scrollDown(AppiumBy.accessibilityId("Container for fragments"));
    driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart")).click();

    Assertions.assertEquals(
        "1",
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV")).getText(),
        "Item not correctly added to cart");
  }

  @Test
  @DisplayName("Remove product from cart")
  public void removeFromCart() {
    driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productIV")).click();
    scrollDown(AppiumBy.accessibilityId("Container for fragments"));
    driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart")).click();

    driver.findElement(AppiumBy.accessibilityId("View cart")).click();

    driver.findElement(AppiumBy.accessibilityId("Removes product from cart")).click();

    Assertions.assertTrue(
        driver
            .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/noItemTitleTV"))
            .isDisplayed(),
        "Shopping Cart is not empty");
  }
}
