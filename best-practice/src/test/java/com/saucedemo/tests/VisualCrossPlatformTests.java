package com.saucedemo.tests;

import com.pages.CheckoutStepOnePage;
import com.pages.LoginPage;
import com.pages.ProductsPage;
import com.pages.ShoppingCartPage;
import com.saucedemo.Endpoints;
import com.saucedemo.WebTestsBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertNull;

@RunWith(Parameterized.class)
public class VisualCrossPlatformTests extends WebTestsBase {
    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public String browserName;
    @Parameterized.Parameter(2)
    public String browserVersion;
    @Parameterized.Parameter(1)
    public String platform;
    @Parameterized.Parameter(3)
    public String viewportSize;
    // Device name is a property added to know which device resolution is configured
    @Parameterized.Parameter(4)
    public String deviceName;

    @Parameterized.Parameters(name = "{4}")
    public static Collection<Object[]> crossBrowserData() {
        return Arrays.asList(new Object[][]{
                {"Chrome", "Windows 10", "latest", "412x732", "Pixel XL"},
                {"Chrome", "Windows 10", "latest", "412x869", "Galaxy Note 10+"},
                {"Safari", "macOS 10.15", "latest", "375x812", "iPhone X"}
        });
    }

    @Before
    public void setUp() throws Exception {
        MutableCapabilities browserOptions = new MutableCapabilities();
        browserOptions.setCapability(CapabilityType.BROWSER_NAME, browserName);
        browserOptions.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
        browserOptions.setCapability(CapabilityType.PLATFORM_NAME, platform);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceUsername);
        sauceOptions.setCapability("accesskey", sauceAccessKey);
        browserOptions.setCapability("sauce:options", sauceOptions);

        MutableCapabilities visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", "Sauce Demo Java");
        visualOptions.setCapability("viewportSize", viewportSize);
        visualOptions.setCapability("failOnNewStates", false);

        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = Endpoints.getScreenerHub();
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @Test()
    public void visualFlow() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        driver.executeScript("/*@visual.init*/", deviceName);
        loginPage.takeSnapshot();

        loginPage.login("standard_user");
        new ProductsPage(driver).takeSnapshot();

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.visit();
        shoppingCartPage.takeSnapshot();

        CheckoutStepOnePage stepOneCheckoutPage = new CheckoutStepOnePage(driver);
        stepOneCheckoutPage.visit();
        stepOneCheckoutPage.takeSnapshot();

        if (driver == null) {
            return;
        }
        final Map<String, Object> response =
                (Map<String, Object>) driver.executeScript("/*@visual.end*/");
        assertNull(response.get("message"));
    }
}
