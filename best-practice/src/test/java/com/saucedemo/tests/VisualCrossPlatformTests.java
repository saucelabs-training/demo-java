package com.saucedemo.tests;

import com.pages.CheckoutStepOnePage;
import com.pages.LoginPage;
import com.pages.ProductsPage;
import com.pages.ShoppingCartPage;
import com.saucedemo.Endpoints;
import com.saucedemo.WebTestsBase;
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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class VisualCrossPlatformTests extends WebTestsBase {
    public String sauceUsername = System.getenv("SAUCE_USERNAME");
    public String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    public String screenerApiKey = System.getenv("SCREENER_API_KEY");
    String deviceNameValue;

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
    @Parameterized.Parameter(4)
    public String deviceName;

    @Parameterized.Parameters(name = "{4}")
    public static Collection<Object[]> crossBrowserData() {
        return Arrays.asList(new Object[][] {
                { "Chrome", "Windows 10", "latest", "412x732", "Pixel XL" },
                { "Chrome", "Windows 10", "latest", "412x869", "Galaxy Note 10+" },
                { "Safari", "macOS 10.15", "latest", "375x812", "iPhone X" }
        });
    }

    @Before
    public void setUp() throws Exception {
        deviceNameValue = testName.getMethodName().split("\\[", -1)[1];

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
        visualOptions.setCapability("projectName", "Sauce Demo");
        visualOptions.setCapability("viewportSize", viewportSize);
        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = Endpoints.getScreenerHub();
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @Test()
    public void pagesRenderCorrectly() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        getJSExecutor().executeScript("/*@visual.init*/", "Responsive Flows");
        loginPage.takeSnapshot(deviceNameValue);

        loginPage.login("standard_user");
        new ProductsPage(driver).takeSnapshot(deviceNameValue);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        shoppingCartPage.visit();
        shoppingCartPage.takeSnapshot(deviceNameValue);

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(driver);
        stepOne.visit();
        stepOne.takeSnapshot(deviceNameValue);

        Map<String, Object> response = (Map<String, Object>) getJSExecutor().executeScript("/*@visual.end*/");
        assertEquals( true, response.get("passed"));
    }
}
