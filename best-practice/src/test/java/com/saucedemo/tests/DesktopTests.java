package com.saucedemo.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.TimeoutException;

/** Desktop Tests. */
@RunWith(Parameterized.class)
public class DesktopTests extends BaseTest {

  @Test()
  public void loginWorks() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.visit();
    loginPage.login("standard_user");
    assertTrue(new ProductsPage(driver).isDisplayed());
  }

  @Test(expected = TimeoutException.class)
  public void lockedOutUser() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.visit();
    loginPage.login("locked_out_user");
    assertFalse(new ProductsPage(driver).isDisplayed());
  }

  @Test(expected = TimeoutException.class)
  public void invalidCredentials() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.visit();
    loginPage.login("foo_bar_user");
    assertFalse(new ProductsPage(driver).isDisplayed());
  }
}
