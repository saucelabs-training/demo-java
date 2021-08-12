package com.saucedemo.selenium.testng;

import com.saucelabs.saucebindings.options.SauceOptions;
import com.saucelabs.saucebindings.testng.SauceParameterizedBaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Tests whoing how to run cross browser tests with TestNG Sauce Bindings jar when Parameterized.
 */
public class CrossBrowserPlatformTest extends SauceParameterizedBaseTest {
    @DataProvider(name = "sauceBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider() {
        return new Object[][]{
            // Uncomment the other data providers ONLY if you have the relevant Sauce VM concurrency
            new Object[]{"chrome", "latest", "macOS 11.00"},
            new Object[]{"firefox", "latest", "macOS 11.00"},
            new Object[]{"chrome", "latest", "Windows 10"},
            new Object[]{"firefox", "latest", "Windows 10"},
            new Object[]{"chrome", "latest-1", "Windows 10"}
            /*new Object[]{"firefox", "latest-1", "Windows 10"},
            new Object[]{"safari", "12.0", "macOS 10.14"},
            new Object[]{"MicrosoftEdge", "latest", "Windows 10"},
            new Object[]{"firefox", "65.0", "Windows 10"},
            new Object[]{"firefox", "64.0", "macOS 10.14"},
            new Object[]{"firefox", "63.0", "macOS 10.13"},
            new Object[]{"firefox", "62.0", "macOS 10.12"},
            new Object[]{"firefox", "61.0", "macOS 10.13"},*/
        };
    }

    @Override
    protected SauceOptions createSauceOptions(Method method, Object[] parameters) {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setCapability("browserName", parameters[0]);
        sauceOptions.setCapability("browserVersion", parameters[1]);
        sauceOptions.setCapability("platformName", parameters[2]);
        return sauceOptions;
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testCase1(String browser, String browserVersion, String platformName) {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testCase2(String browser, String browserVersion, String platformName) {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }
}
