package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.SystemManager;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test to demonstrate how Sauce Connect works.
 */
public class SauceConnectTest extends SauceBaseTest {
    @Override
    public SauceOptions createSauceOptions() {
        return SauceOptions.chrome()
                .setTunnelIdentifier(SystemManager.get("TUNNEL_IDENTIFIER"))
                .setParentTunnel(SystemManager.get("PARENT_TUNNEL"))
                .build();
    }

    @Ignore("this test only applies when running local app with Sauce Connect")
    @Test
    public void shouldOpen() {
        // In order for Sauce to be able to Securely access your application, we use Sauce Connect
        driver.get("http://localhost:3000");
        assertEquals("React App", driver.getTitle());
    }
}
