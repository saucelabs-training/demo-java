package com.saucedemo;

import io.appium.java_client.AppiumBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NavigationTest extends TestBase {

  @Test
  @DisplayName("Navigate to WebView Page")
  public void webViewNavigation() {
    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    // driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawerMenu"));

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));
    driver
        .findElement(
            AppiumBy.xpath(
                "//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/itemTV\" and @text=\"WebView\"]"))
        .click();
    Assertions.assertTrue(
        driver
            .findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/webViewTV"))
            .isDisplayed(),
        "Navigation unsuccessful");
  }

  @Test
  @DisplayName("Navigate to About Page")
  public void aboutNavigation() {
    driver.findElement(AppiumBy.accessibilityId("View menu")).click();
    // driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawerMenu"));

    scrollDown(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuRV"));

    driver
        .findElement(
            AppiumBy.xpath(
                "//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/itemTV\" and @text=\"About\"]"))
        .click();

    Assertions.assertTrue(
        driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/aboutTV")).isDisplayed(),
        "Navigation unsuccessful");
  }
}
