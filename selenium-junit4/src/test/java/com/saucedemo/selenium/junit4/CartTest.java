package com.saucedemo.selenium.junit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class CartTest extends TestBase {

  @Before
  public void setup() {
    startChromeSession();
  }

  @Test
  public void addFromProductPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

    driver.findElement(By.id("item_1_title_link")).click();

    driver
        .findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bolt-t-shirt']"))
        .click();

    Assert.assertEquals(
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
    driver.findElement(By.id("item_1_title_link")).click();
    driver
        .findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-bolt-t-shirt']"))
        .click();

    driver
        .findElement(By.cssSelector("button[data-test='remove-sauce-labs-bolt-t-shirt']"))
        .click();

    Assert.assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
  }

  @Test
  public void addFromInventoryPage() {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

    driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-onesie']")).click();

    Assert.assertEquals("1", driver.findElement(By.className("shopping_cart_badge")).getText());
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

    Assert.assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
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

    Assert.assertTrue(driver.findElements(By.className("shopping_cart_badge")).isEmpty());
  }
}
