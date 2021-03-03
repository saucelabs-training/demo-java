package com.saucedemo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
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
                { "Chrome", "Windows 10", "latest", "412x732", "Pixel" },
                { "Chrome", "Windows 10", "latest", "412x869", "Galaxy Note 10+" },
                { "Chrome", "Windows 10", "latest", "412x869", "Pixel 4 XL" },
                { "Chrome", "Windows 10", "latest", "412x869", "Pixel 4" },
                { "Safari", "macOS 10.15", "latest", "375x812", "iPhone X" },
                { "Safari", "macOS 10.15", "latest", "414x736", "iPhone 8 Plus" },
                { "Safari", "macOS 10.15", "latest", "375x667", "iPhone 8" },
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
        visualOptions.setCapability("projectName", "Screener.io");
        visualOptions.setCapability("viewportSize", viewportSize);
        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, browserOptions);
    }

    @Test()
    public void mainJourneyTest() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        String deviceName = testName.getMethodName().split("\\[", -1)[1];
        webDriver.get("https://screener.io");
        updateElementText("oopsie doopsy", "btn-primary");

        js.executeScript("/*@visual.init*/", "Responsive Flows");
        js.executeScript("/*@visual.snapshot*/", "Home Page" + ":" + deviceName);

        webDriver.get("https://screener.io/v2/docs");
        js.executeScript("/*@visual.snapshot*/", "Docs Page" + ":" + deviceName);

        webDriver.get("https://screener.io/pricing");
        js.executeScript("/*@visual.snapshot*/", "Components Pricing" + ":" + deviceName);

        Map<String, Object> response = (Map<String, Object>) js.executeScript("/*@visual.end*/");
        assertEquals( true, response.get("passed"));
    }

    private void updateElementText(String newText, String className) {
        ((JavascriptExecutor) webDriver).executeScript(
                "document.getElementsByClassName('" + className +
                        "\"')[0].innerText = \'" + newText + "\';");
    }
}
