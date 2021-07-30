package com.saucedemo;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.*;

public class VisualE2ETests {

    private RemoteWebDriver webDriver;
    public String sauceUsername = System.getenv("SAUCE_USERNAME");
    public String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    public String screenerApiKey = System.getenv("SCREENER_API_KEY");
    private MutableCapabilities browserOptions;

    public VisualE2ETests(){}

    @Before
    public void setUp() {
        browserOptions = new MutableCapabilities();
        browserOptions.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        browserOptions.setCapability(CapabilityType.BROWSER_VERSION, "latest");
        browserOptions.setCapability(CapabilityType.PLATFORM_NAME, "Windows 10");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceUsername);
        sauceOptions.setCapability("accesskey", sauceAccessKey);
        browserOptions.setCapability("sauce:options", sauceOptions);
    }

    @Test
    public void testVisualE2E() throws MalformedURLException {
        MutableCapabilities visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", "Visual Demo Java");
        visualOptions.setCapability("viewportSize", "1280x1024");
        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, browserOptions);

        webDriver.get("https://screener.io");

        webDriver.executeScript("/*@visual.init*/", "My Visual Test 2");
        webDriver.executeScript("/*@visual.snapshot*/", "Home");
        assertNoVisualDifferences();
    }

    @Test
    public void visualBaselineBranching() throws MalformedURLException {
        //*
        // This example shows how a team might have a baseline snapshot in Prod
        // that is compared against a snapshot in QA
        // **/
        String projectName = "visual-examples-java";
        MutableCapabilities visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", projectName);
        visualOptions.setCapability("viewportSize", "1280x1024");
        visualOptions.setCapability("branch", "main");
        visualOptions.setCapability("baseBranch", "main");
        visualOptions.setCapability("alwaysAcceptBaseBranch", true);
        browserOptions.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, browserOptions);

        webDriver.get("https://www.saucedemo.com");

        // Capture a snapshot of a page on the main branch
        webDriver.executeScript("/*@visual.init*/", "My Visual Test");
        webDriver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();

        //Capture a snapshot on a different branch
        visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", projectName);
        visualOptions.setCapability("viewportSize", "1280x1024");
        visualOptions.setCapability("branch", "newBranch");
        visualOptions.setCapability("baseBranch", "main");
        browserOptions.setCapability("sauce:visual", visualOptions);

        url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, browserOptions);
        webDriver.get("https://www.screener.io");

        // Capture a snapshot of a page on the main branch
        webDriver.executeScript("/*@visual.init*/", "My Visual Test");
        webDriver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();
    }

    private void assertNoVisualDifferences() {
        final Map<String, Object> response =
                (Map<String, Object>) webDriver.executeScript("/*@visual.end*/");
        if(response.get("message") != null){
            assertNull(response.get("message").toString());
        }
    }
}
