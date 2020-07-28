package com.saucedemo;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginFeatureTest extends BaseTest {

    @Test
    public void shouldBeAbleToLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isLoaded());
    }
    @Test
    public void doesntLoginWithBadUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("fake_user", "secret_sauce");
        Assert.assertFalse(inventoryPage.isLoaded());
    }
    @Test
    public void cantLoginWithLockedOutUser() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertFalse(inventoryPage.isLoaded());
    }
    @Test
    public void shouldBeAbleToLogin2() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isLoaded());
    }
    @Test
    public void doesntLoginWithBadUser2() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("fake_user", "secret_sauce");
        Assert.assertFalse(inventoryPage.isLoaded());
    }
    @Test
    public void cantLoginWithLockedOutUser2() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertFalse(inventoryPage.isLoaded());
    }
    @Test
    public void shouldBeAbleToLogin3() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isLoaded());
    }
    @Test
    public void doesntLoginWithBadUser3() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("fake_user", "secret_sauce");
        Assert.assertFalse(inventoryPage.isLoaded());
    }
    @Test
    public void cantLoginWithLockedOutUser3() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertFalse(inventoryPage.isLoaded());
    }
}
