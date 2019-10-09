package tests;

import pages.InventoryPage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseWebDriverTest {
    @Test
    public void loginTestValid() {
        LoginPage loginPage = getNavigation().goToLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = getNavigation().getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test
    public void loginTestValidPerfGlitch() {
        LoginPage loginPage = getNavigation().goToLoginPage();
        loginPage.login("performance_glitch_user", "secret_sauce");
        InventoryPage inventoryPage = getNavigation().getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test
    public void loginTestValidLockedOut() {
        LoginPage loginPage = getNavigation().goToLoginPage();
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.epicSadFaceDisplayed());
    }

    @Test
    public void loginTestValidProblem() {
        LoginPage loginPage = getNavigation().goToLoginPage();
        loginPage.login("problem_user", "secret_sauce");
        InventoryPage inventoryPage = getNavigation().getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test
    public void loginTestInvalidUsername() {
        LoginPage loginPage = getNavigation().goToLoginPage();
        loginPage.login("invalid_user", "secret_sauce");
        Assert.assertTrue(loginPage.isOnPage());
    }

    @Test
    public void loginTestInvalidPassword() {
        LoginPage loginPage = getNavigation().goToLoginPage();
        loginPage.login("standard_user", "invalid_password");
        Assert.assertTrue(loginPage.isOnPage());
    }
}