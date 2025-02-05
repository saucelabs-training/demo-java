package com.saucedemo.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckoutTest extends TestBase {

  @Test
  public void badInfo() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
    page.locator("[data-test='add-to-cart-sauce-labs-onesie']").click();
    page.locator(".shopping_cart_link").click();
    page.locator("[data-test='checkout']").click();

    page.locator("[data-test='continue']").click();

    Assertions.assertTrue(
        page.locator("[data-test='firstName']").getAttribute("class").contains("error"),
        "Expected error not found on page");
  }

  @Test
  public void goodInfo() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
    page.locator("[data-test='add-to-cart-sauce-labs-onesie']").click();
    page.locator(".shopping_cart_link").click();
    page.locator("[data-test='checkout']").click();

    page.locator("[data-test='firstName']").fill("Luke");
    page.locator("[data-test='lastName']").fill("Perry");
    page.locator("[data-test='postalCode']").fill("90210");

    page.locator("[data-test='continue']").click();

    Assertions.assertEquals(
        "https://www.saucedemo.com/checkout-step-two.html",
        page.url(),
        "Information Submission Unsuccessful");
  }

  @Test
  public void completeCheckout() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
    page.locator("[data-test='add-to-cart-sauce-labs-onesie']").click();
    page.locator(".shopping_cart_link").click();
    page.locator("[data-test='checkout']").click();
    page.locator("[data-test='firstName']").fill("Luke");
    page.locator("[data-test='lastName']").fill("Perry");
    page.locator("[data-test='postalCode']").fill("90210");
    page.locator("[data-test='continue']").click();

    page.locator("[data-test='finish']").click();

    Assertions.assertEquals("https://www.saucedemo.com/checkout-complete.html", page.url());

    Assertions.assertTrue(page.locator(".complete-text").isVisible());
  }
}
