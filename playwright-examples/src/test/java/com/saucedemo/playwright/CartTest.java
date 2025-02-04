package com.saucedemo.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartTest extends TestBase {

  @Test
  public void addFromProductPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();

    page.locator("[data-test='add-to-cart-sauce-labs-bolt-t-shirt']").click();

    Assertions.assertEquals(
            "1",
            page.locator(".shopping_cart_badge").textContent(),
            "Item not correctly added to cart");
  }

  @Test
  public void removeFromProductPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
    page.locator("[data-test='add-to-cart-sauce-labs-bolt-t-shirt']").click();

    page.locator("[data-test='remove-sauce-labs-bolt-t-shirt']").click();

    Assertions.assertEquals(0,
            page.locator(".shopping_cart_badge").count(),
            "Item not correctly removed from cart");
  }

  @Test
  public void addFromInventoryPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();

    page.locator("[data-test='add-to-cart-sauce-labs-onesie']").click();

    Assertions.assertEquals("1", page.locator(".shopping_cart_badge").textContent());
  }

  @Test
  public void removeFromInventoryPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
    page.locator("[data-test='add-to-cart-sauce-labs-bike-light']").click();

    page.locator("[data-test='remove-sauce-labs-bike-light']").click();

    Assertions.assertEquals(0,
            page.locator(".shopping_cart_badge").count(),
            "Shopping Cart is not empty");
  }

  @Test
  public void removeFromCartPage() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
    page.locator("[data-test='add-to-cart-sauce-labs-backpack']").click();
    page.locator(".shopping_cart_link").click();

    page.locator("[data-test='remove-sauce-labs-backpack']").click();

    Assertions.assertEquals(0,
            page.locator(".shopping_cart_badge").count(),
            "Shopping Cart is not empty");
  }
}
