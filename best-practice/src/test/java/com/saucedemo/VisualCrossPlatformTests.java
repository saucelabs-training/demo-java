package com.saucedemo;

import com.pages.CheckoutStepOnePage;
import com.pages.LoginPage;
import com.pages.ProductsPage;
import com.pages.ShoppingCartPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class VisualCrossPlatformTests {

    protected WebDriver webDriver;
    public String sauceUsername = System.getenv("SAUCE_USERNAME");
    public String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    public String screenerApiKey = System.getenv("SCREENER_API_KEY");

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

    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

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
        visualOptions.setCapability("projectName", "Sauce Demo");
        visualOptions.setCapability("viewportSize", viewportSize);
        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = Endpoints.getScreenerHub();
        webDriver = new RemoteWebDriver(url, browserOptions);
    }

    @Test()
    public void pagesRenderCorrectly() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        String deviceName = testName.getMethodName().split("\\[", -1)[1];

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.visit();
        js.executeScript("/*@visual.init*/", "Responsive Flows");
        loginPage.takeSnapshot(deviceName);

        loginPage.login("standard_user");
        new ProductsPage(webDriver).takeSnapshot(deviceName);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(webDriver);
        shoppingCartPage.visit();
        shoppingCartPage.takeSnapshot(deviceName);

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(webDriver);
        stepOne.visit();
        stepOne.takeSnapshot(deviceName);

        Map<String, Object> response = (Map<String, Object>) js.executeScript("/*@visual.end*/");
        assertEquals( true, response.get("passed"));
    }
}
