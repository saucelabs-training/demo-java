package com.saucedemo.playwright;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthenticationTest extends TestBase {

  @Test
  public void signInUnsuccessful() {
    page.navigate("https://www.saucedemo.com/");

    page.fill("[data-test='username']", "locked_out_user");
    page.fill("[data-test='password']", "secret_sauce");
    page.click("[data-test='login-button']");

    String errorText = page.textContent("[data-test='error']");
    Assertions.assertTrue(
        errorText.contains("Sorry, this user has been locked out"), "Error Not Found");
  }

  @Test
  public void signInSuccessful() {
    page.navigate("https://www.saucedemo.com/");

    page.fill("[data-test='username']", "standard_user");
    page.fill("[data-test='password']", "secret_sauce");
    page.click("[data-test='login-button']");

    Assertions.assertEquals(
        "https://www.saucedemo.com/inventory.html", page.url(), "Login Not Successful");
  }

  @Test
  public void logout() throws InterruptedException {
    page.navigate("https://www.saucedemo.com/");
    page.fill("[data-test='username']", "standard_user");
    page.fill("[data-test='password']", "secret_sauce");
    page.click("[data-test='login-button']");

    page.click("#react-burger-menu-btn");
    page.click("#logout_sidebar_link");

    Assertions.assertEquals("https://www.saucedemo.com/", page.url(), "Logout Not Successful");
  }
}
