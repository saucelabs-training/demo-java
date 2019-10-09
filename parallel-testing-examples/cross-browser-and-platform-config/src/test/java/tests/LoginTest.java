package tests;

import pages.SauceDemoNavigation;
import pages.InventoryPage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseWebDriverTest {
    @Test(dataProvider = "sauceBrowsers")
    public void loginTestValid(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "loginTestValid", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestValidPerfGlitch(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "loginTestValidPerfGlitch", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("performance_glitch_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestValidLockedOut(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "loginTestValidLockedOut", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.epicSadFaceDisplayed());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestValidProblem(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "loginTestValidProblem", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("problem_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestInvalidUsername(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "loginTestInvalidUsername", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("invalid_user", "secret_sauce");
        Assert.assertTrue(loginPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestInvalidPassword(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "loginTestInvalidPassword", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("standard_user", "invalid_password");
        Assert.assertTrue(loginPage.isOnPage());
    }
}