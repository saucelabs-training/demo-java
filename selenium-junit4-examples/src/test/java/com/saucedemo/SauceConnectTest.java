package com.saucedemo;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SauceConnectTest {
    protected WebDriver driver;
    private SauceSession session;

    @Test
    public void shouldOpen() {
        SauceOptions options = new SauceOptions();
        // When we start a tunnel, we provide a tunnedIdentifier using the '-i' flag
        // And we can reference that tunnel here
        options.setTunnelIdentifier("NikolaysTunnel");
        options.setName("sauceConnectTest");

        session = new SauceSession(options);
        driver = session.start();
        // This is an example of an application that Sauce Labs cannot get to because it's in your internal network
        // In order for Sauce to be able to Securely access your application, we use Sauce Connect
        driver.get("http://localhost:3000");
        assertEquals("React App", driver.getTitle());
    }
    @After
    public void teardown() {
        session.stop(true);
    }
}
