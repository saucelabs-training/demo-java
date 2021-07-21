package com.saucedemo.selenium.testng;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrossBrowserPlatformTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private ThreadLocal<SauceSession> session = new ThreadLocal<>();

    private static String buildName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    /**
     * DataProvider that sets the browser combinations to be used.
     *
     * @return TestNG's preferred Object[][] structure, containing browser, version, and platform information
     */
    @DataProvider(name = "sauceBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider() {
        return new Object[][]{
                /** Uncomment the other data providers ONLY if you have the relevant Sauce VM concurrency **/
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


    @BeforeMethod
    public void setup(Method method, Object[] parameters) {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setCapability("browserName", parameters[0]);
        sauceOptions.setCapability("browserVersion", parameters[1]);
        sauceOptions.setCapability("platformName", parameters[2]);
        sauceOptions.setName(method.getName());
        sauceOptions.setBuild(buildName);

        session.set(new SauceSession(sauceOptions));
        driver.set(session.get().start());
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testCase1(String browser, String browserVersion, String platformName) {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @Test(dataProvider = "sauceBrowsers")
    public void testCase2(String browser, String browserVersion, String platformName) {
        driver.get().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.get().getTitle(), "Swag Labs");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        session.get().stop(result.isSuccess());
    }
}
