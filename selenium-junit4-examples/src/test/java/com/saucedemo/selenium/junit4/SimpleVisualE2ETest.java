package com.saucedemo.selenium.junit4;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for running Visual Tests on Sauce.
 */
public class SimpleVisualE2ETest {

    protected RemoteWebDriver driver;
    public String sauceUsername = System.getenv("SAUCE_USERNAME");
    public String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
    public String screenerApiKey = System.getenv("SCREENER_API_KEY");
    private MutableCapabilities capabilities;
    private MutableCapabilities visualOptions;

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

        visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("viewportSize", "1280x1024");
    }

    @Test
    public void testVisualE2E() throws MalformedURLException {
        MutableCapabilities visualOptions = new MutableCapabilities();
        visualOptions.setCapability("apiKey", screenerApiKey);
        visualOptions.setCapability("projectName", "visual-e2e-test");
        visualOptions.setCapability("viewportSize", "1280x1024");
        visualOptions.setCapability("failOnNewStates", "false");
        capabilities.setCapability("sauce:visual", visualOptions);
        // Visual requires validation when the browser version changes
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, "94");

        URL url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);

        driver.get("https://saucedemo.com");

        driver.executeScript("/*@visual.init*/", "testVisualE2E()");
        driver.executeScript("/*@visual.snapshot*/", "Home");
        Map<String, Object> response = (Map<String, Object>) driver.executeScript("/*@visual.end*/");
        assertNull(response.get("message"));
    }

    @Test
    public void samePagesDifferentBranches() throws MalformedURLException {
        //*
        // This example shows how a team might have a baseline snapshot in Prod
        // that is compared against a snapshot in QA
        // Since this test is against the same page, all snapshots should pass
        // **/
        String projectName = "samePagesDifferentBranches()";
        visualOptions.setCapability("projectName", projectName);
        visualOptions.setCapability("branch", "main");
        visualOptions.setCapability("baseBranch", "main");
        visualOptions.setCapability("alwaysAcceptBaseBranch", true);
        capabilities.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);

        driver.get("https://www.saucedemo.com");

        // Capture a snapshot of a page on the main branch
        driver.executeScript("/*@visual.init*/", "My Visual Test");
        driver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();

        //Capture a snapshot on a different branch
        visualOptions.setCapability("branch", "2ndBranch");
        visualOptions.setCapability("baseBranch", "main");
        capabilities.setCapability("sauce:visual", visualOptions);
        url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("https://www.saucedemo.com");
        driver.executeScript("/*@visual.init*/", "My Visual Test");
        driver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();

        visualOptions.setCapability("branch", "3rdBranch");
        visualOptions.setCapability("baseBranch", "main");
        capabilities.setCapability("sauce:visual", visualOptions);
        url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("https://www.saucedemo.com");
        driver.executeScript("/*@visual.init*/", "My Visual Test");
        driver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();
    }

    @Test
    public void differentPagesDifferentBranches() throws MalformedURLException {
        //*
        // This example shows how a team might have a baseline snapshot in Prod
        // that is compared against a snapshot in QA
        // Since this test is against different pages, all snapshots should fail
        // **/
        String projectName = "differentPagesDifferentBranches()";
        visualOptions.setCapability("projectName", projectName);
        visualOptions.setCapability("branch", "main");
        visualOptions.setCapability("baseBranch", "main");
        visualOptions.setCapability("alwaysAcceptBaseBranch", true);
        capabilities.setCapability("sauce:visual", visualOptions);

        URL url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);

        driver.get("https://www.saucedemo.com");

        // Capture a snapshot of a page on the main branch
        driver.executeScript("/*@visual.init*/", "My Visual Test");
        driver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertNoVisualDifferences();

        //Capture a snapshot on a different branch
        visualOptions.setCapability("branch", "2ndBranch");
        visualOptions.setCapability("baseBranch", "main");
        capabilities.setCapability("sauce:visual", visualOptions);
        url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("https://www.screener.io");
        driver.executeScript("/*@visual.init*/", "My Visual Test");
        driver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertHasVisualDifferences();

        visualOptions.setCapability("branch", "3rdBranch");
        visualOptions.setCapability("baseBranch", "main");
        capabilities.setCapability("sauce:visual", visualOptions);
        url = new URL("https://hub.screener.io/wd/hub");
        driver = new RemoteWebDriver(url, capabilities);
        driver.get("https://www.saucelabs.com");
        driver.executeScript("/*@visual.init*/", "My Visual Test");
        driver.executeScript("/*@visual.snapshot*/", "Branch Compare");
        assertHasVisualDifferences();
    }

    private void assertNoVisualDifferences() {
        Map<String, Object> response = (Map<String, Object>) driver.executeScript("/*@visual.end*/");
        if (response.get("message") != null) {
            assertNull(response.get("message"));
        }
    }

    private void assertHasVisualDifferences() {
        Map<String, Object> response = (Map<String, Object>) driver.executeScript("/*@visual.end*/");
        assertEquals(false, response.get("passed"));
    }
}
