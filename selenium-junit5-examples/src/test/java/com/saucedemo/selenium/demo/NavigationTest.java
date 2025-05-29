package com.saucedemo.selenium.demo;

import com.saucedemo.selenium.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

public class NavigationTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startFirefoxSession(testInfo);
  }

  @Test
  public void cancelFromCart() {
    driver.navigate().to("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver.findElement(By.className("shopping_cart_link")).click();

    driver.findElement(By.cssSelector("button[data-test='continue-shopping']")).click();

    Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
  }

  @Test
  public void cancelFromInfoPage() {
    driver.navigate().to("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-onesie']")).click();
    driver.findElement(By.className("shopping_cart_link")).click();
    driver.findElement(By.cssSelector("button[data-test='checkout']")).click();

    driver.findElement(By.cssSelector("button[data-test='cancel']")).click();

    Assertions.assertEquals("https://www.saucedemo.com/cart.html", driver.getCurrentUrl());
  }

  @Test
  public void cancelFromCheckoutPage() {
    driver.navigate().to("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-onesie']")).click();
    driver.findElement(By.className("shopping_cart_link")).click();
    driver.findElement(By.cssSelector("button[data-test='checkout']")).click();
    driver.findElement(By.cssSelector("input[data-test='firstName']")).sendKeys("Luke");
    driver.findElement(By.cssSelector("input[data-test='lastName']")).sendKeys("Perry");
    driver.findElement(By.cssSelector("input[data-test='postalCode']")).sendKeys("90210");
    driver.findElement(By.cssSelector("input[data-test='continue']")).click();

    driver.findElement(By.cssSelector("button[data-test='cancel']")).click();

    Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
  }

  @Test
  public void startCheckout() {
    driver.navigate().to("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver.findElement(By.cssSelector("button[data-test='add-to-cart-sauce-labs-onesie']")).click();
    driver.findElement(By.className("shopping_cart_link")).click();

    driver.findElement(By.cssSelector("button[data-test='checkout']")).click();

    Assertions.assertEquals(
        "https://www.saucedemo.com/checkout-step-one.html", driver.getCurrentUrl());
  }
}
