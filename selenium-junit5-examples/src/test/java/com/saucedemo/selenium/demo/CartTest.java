package com.saucedemo.selenium.demo;

import com.saucedemo.selenium.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

public class CartTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @Test
  public void addFromProductPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

    driver
        .findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bolt-t-shirt']"))
        .click();

    Assertions.assertEquals(
        "1",
        driver.findElement(By.className("shopping_cart_badge")).getText(),
        "Item not correctly added to cart");
  }

  @Test
  public void removeFromProductPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver
        .findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bolt-t-shirt']"))
        .click();

    driver
        .findElement(By.cssSelector("button[data-test='remove-sauce-labs-bolt-t-shirt']"))
        .click();

    Assertions.assertTrue(
        driver.findElements(By.className("shopping_cart_badge")).isEmpty(),
        "Item not correctly removed from cart");
  }

  @Test
  public void addFromInventoryPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

    driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-onesie']")).click();

    Assertions.assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
  }

  @Test
  public void removeFromInventoryPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver
        .findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bike-light']"))
        .click();

    driver.findElement(By.cssSelector("button[data-test='remove-sauce-labs-bike-light']")).click();

    Assertions.assertTrue(
        driver.findElements(By.className("shopping_cart_badge")).isEmpty(),
        "Shopping Cart is not empty");
  }

  @Test
  public void removeFromCartPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver
        .findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']"))
        .click();
    driver.findElement(By.className("shopping_cart_link")).click();

    driver.findElement(By.cssSelector("button[data-test='remove-sauce-labs-backpack']")).click();

    Assertions.assertTrue(
        driver.findElements(By.className("shopping_cart_badge")).isEmpty(),
        "Shopping Cart is not empty");
  }
}
