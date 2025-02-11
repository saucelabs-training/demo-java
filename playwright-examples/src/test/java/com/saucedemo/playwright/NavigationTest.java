package com.saucedemo.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NavigationTest extends TestBase {

  @Test
  public void cancelFromCart() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("input[data-test='username']").fill("standard_user");
    page.locator("input[data-test='password']").fill("secret_sauce");
    page.locator("input[data-test='login-button']").click();
    page.locator(".shopping_cart_link").click();

    page.locator("button[data-test='continue-shopping']").click();

    Assertions.assertEquals("https://www.saucedemo.com/inventory.html", page.url());
  }

  @Test
  public void cancelFromInfoPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("input[data-test='username']").fill("standard_user");
    page.locator("input[data-test='password']").fill("secret_sauce");
    page.locator("input[data-test='login-button']").click();
    page.locator("button[data-test='add-to-cart-sauce-labs-onesie']").click();
    page.locator(".shopping_cart_link").click();
    page.locator("button[data-test='checkout']").click();

    page.locator("button[data-test='cancel']").click();

    Assertions.assertEquals("https://www.saucedemo.com/cart.html", page.url());
  }

  @Test
  public void cancelFromCheckoutPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("input[data-test='username']").fill("standard_user");
    page.locator("input[data-test='password']").fill("secret_sauce");
    page.locator("input[data-test='login-button']").click();
    page.locator("button[data-test='add-to-cart-sauce-labs-onesie']").click();
    page.locator(".shopping_cart_link").click();
    page.locator("button[data-test='checkout']").click();
    page.locator("input[data-test='firstName']").fill("Luke");
    page.locator("input[data-test='lastName']").fill("Perry");
    page.locator("input[data-test='postalCode']").fill("90210");
    page.locator("input[data-test='continue']").click();

    page.locator("button[data-test='cancel']").click();

    Assertions.assertEquals("https://www.saucedemo.com/inventory.html", page.url());
  }

  @Test
  public void startCheckout() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("input[data-test='username']").fill("standard_user");
    page.locator("input[data-test='password']").fill("secret_sauce");
    page.locator("input[data-test='login-button']").click();
    page.locator("button[data-test='add-to-cart-sauce-labs-onesie']").click();
    page.locator(".shopping_cart_link").click();

    page.locator("button[data-test='checkout']").click();

    Assertions.assertEquals("https://www.saucedemo.com/checkout-step-one.html", page.url());
  }
}
