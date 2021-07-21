package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

public class SauceConnectTest {
    protected RemoteWebDriver driver;
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
