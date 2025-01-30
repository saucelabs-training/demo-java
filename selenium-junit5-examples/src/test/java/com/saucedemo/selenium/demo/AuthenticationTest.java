package com.saucedemo.selenium.demo;

import com.saucedemo.selenium.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AuthenticationTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startFirefoxSession(testInfo);
  }

  @Test
  public void signInUnsuccessful() {
    driver.get("https://www.saucedemo.com/");

    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("locked_out_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

    WebElement errorElement = driver.findElement(By.cssSelector("[data-test='error']"));
    Assertions.assertTrue(
        errorElement.getText().contains("Sorry, this user has been locked out"), "Error Not Found");
  }

  @Test
  public void signInSuccessful() {
    driver.get("https://www.saucedemo.com/");

    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();

    Assertions.assertEquals(
        "https://www.saucedemo.com/inventory.html", driver.getCurrentUrl(), "Login Not Successful");
  }

  @Test
  public void logout() throws InterruptedException {
    driver.get("https://www.saucedemo.com/");
    driver.findElement(By.cssSelector("input[data-test='username']")).sendKeys("standard_user");
    driver.findElement(By.cssSelector("input[data-test='password']")).sendKeys("secret_sauce");
    driver.findElement(By.cssSelector("input[data-test='login-button']")).click();
    driver.findElement(By.id("react-burger-menu-btn")).click();
    Thread.sleep(1000);

    driver.findElement(By.id("logout_sidebar_link")).click();

    Assertions.assertEquals(
        "https://www.saucedemo.com/", driver.getCurrentUrl(), "Logout Not Successful");
  }
}
