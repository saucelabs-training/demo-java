package tests;

import com.saucedemo.pages.SauceDemoNavigation;
import com.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class InventoryTest extends BaseWebDriverTest {
    @Test(dataProvider = "sauceBrowsers")
    public void testItem3Label(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "testItem3Label", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());

        navigation.goToLoginPage().login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertEquals("Sauce Labs Bolt T-Shirt", inventoryPage.getItemName(3));
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testItem3Price(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "testItem3Price", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        navigation.goToLoginPage().login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertEquals("$15.99", inventoryPage.getItemPrice(3));
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testItem3Description(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "testItem3Description", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        navigation.goToLoginPage().login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        Assert.assertEquals("Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.", inventoryPage.getItemDescription(3));
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testAddOneItem(String browser, String browserVersion, String platformName, RunType runType) {
        this.createDriver(browser, browserVersion, platformName, "testAddOneItem", runType);

        SauceDemoNavigation navigation = new SauceDemoNavigation(getWebDriver());
        navigation.goToLoginPage().login("standard_user", "secret_sauce");
        InventoryPage inventoryPage = navigation.getInventoryPage();
        inventoryPage.addItemToCart(3);
        Assert.assertTrue(inventoryPage.itemAddedToCart(3));
    }
}
