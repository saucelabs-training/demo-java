package com.saucedemo.selenium.junit4;

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

public class SimpleVisualE2ETest {

    protected WebDriver webDriver;
    public String sauceUsername = System.getenv("SAUCE_USERNAME");
    public String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    public String screenerApiKey = System.getenv("SCREENER_API_KEY");
    private MutableCapabilities capabilities;

    public JavascriptExecutor getJsExecutor()
    {
        return (JavascriptExecutor) webDriver;
    }

    @Before
    public void setUp() {
        capabilities = new MutableCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, "latest");
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, "Windows 10");

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceUsername);
        sauceOptions.setCapability("accesskey", sauceAccessKey);
        capabilities.setCapability("sauce:options", sauceOptions);
    }

    @Test
    public void testVisualE2E() throws MalformedURLException {
        MutableCapabilities visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", "visual-e2e-test");
        visualOptions.setCapability("viewportSize", "1280x1024");
        capabilities.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, capabilities);

        webDriver.get("https://screener.io");

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("/*@visual.init*/", "My Visual Test 2");
        js.executeScript("/*@visual.snapshot*/", "Home");
        Map<String, Object> response = (Map<String, Object>) js.executeScript("/*@visual.end*/");
        assertNull(response.get("message").toString());
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
        capabilities.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, capabilities);

        webDriver.get("https://www.saucedemo.com");

        // Capture a snapshot of a page on the main branch
        getJsExecutor().executeScript("/*@visual.init*/", "My Visual Test");
        getJsExecutor().executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();

        //Capture a snapshot on a different branch
        visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", projectName);
        visualOptions.setCapability("viewportSize", "1280x1024");
        visualOptions.setCapability("branch", "newBranch");
        visualOptions.setCapability("baseBranch", "main");
        capabilities.setCapability("sauce:visual", visualOptions);

        url = new URL("https://hub.screener.io/wd/hub");
        webDriver = new RemoteWebDriver(url, capabilities);
        webDriver.get("https://www.screener.io");

        // Capture a snapshot of a page on the main branch
        getJsExecutor().executeScript("/*@visual.init*/", "My Visual Test");
        getJsExecutor().executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();
    }

    private void assertNoVisualDifferences() {
        Map<String, Object> response = (Map<String, Object>) getJsExecutor().executeScript("/*@visual.end*/");
        if(response.get("message") != null){
            assertNull(response.get("message").toString());
        }
    }
}
