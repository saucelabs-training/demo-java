package com.yourcompany.tests;

import com.yourcompany.listeners.SauceTestListener;
import com.yourcompany.pages.InventoryPage;
import com.yourcompany.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest extends SauceTestListener {

    @Test(groups = { "@Web1", "@Login" })
    public void loginTestValid() {

        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.navigate();
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(this.getDriver());
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(groups = { "@Web" , "@Login"})
    public void loginTestValidPerfGlitch() {

        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.navigate();
        loginPage.login("performance_glitch_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(this.getDriver());
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(groups = { "@Web" })
    public void loginTestValidLockedOut() {

        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.navigate();
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.epicSadFaceDisplayed());
    }

    @Test(groups = { "@Web" })
    public void loginTestValidProblem() {

        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.navigate();
        loginPage.login("problem_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(this.getDriver());
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(groups = { "@Web" })
    public void loginTestInvalidUsername() {

        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.navigate();
        loginPage.login("invalid_user", "secret_sauce");

        Assert.assertTrue(loginPage.isOnPage());
    }

    @Test(groups = { "@Web1" })
    public void loginTestInvalidPassword() {

        LoginPage loginPage = new LoginPage(this.getDriver());
        loginPage.navigate();
        loginPage.login("standard_user", "invalid_password");

        Assert.assertTrue(loginPage.isOnPage());
    }
}