package example.ios.Tests.simulator;

import example.ios.Pages.web.InventoryPage;
import example.ios.Pages.web.LoginPage;
import example.ios.Pages.web.SauceDemoNavigation;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseWebDriverTest {
    @Test(dataProvider = "sauceBrowsers")
    public void loginTestValid(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName) {
        this.createDriver(browser, platformName, platformVersion, deviceOrientation, deviceName, "loginTestValid");

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestPeformanceGlitch(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName) {
        this.createDriver(browser, platformName, platformVersion, deviceOrientation, deviceName, "loginTestPeformanceGlitch");

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("performance_glitch_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestLockedOut(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName) {
        this.createDriver(browser, platformName, platformVersion, deviceOrientation, deviceName, "loginTestLockedOut");

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.epicSadFaceDisplayed());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestProblemUser(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName) {
        this.createDriver(browser, platformName, platformVersion, deviceOrientation, deviceName, "loginTestProblemUser");

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("problem_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertTrue(inventoryPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestInvalid(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName) {
        this.createDriver(browser, platformName, platformVersion, deviceOrientation, deviceName, "loginTestInvalidUser");

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("invalid_user", "secret_sauce");
        Assert.assertTrue(loginPage.isOnPage());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void loginTestInvalidPassword(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName) {
        this.createDriver(browser, platformName, platformVersion, deviceOrientation, deviceName, "loginTestInvalidPassword");

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        LoginPage loginPage = navigation.goToLoginPage();
        loginPage.login("standard_user", "invalid_password");
        Assert.assertTrue(loginPage.isOnPage());
    }
}