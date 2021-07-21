package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.SystemManager;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceConnectTest extends SauceBaseTest {
    @Override
    public SauceOptions createSauceOptions() {
        return SauceOptions.chrome()
                .setTunnelIdentifier(SystemManager.get("TUNNEL_IDENTIFIER"))
                .setParentTunnel(SystemManager.get("PARENT_TUNNEL"))
                .build();
    }

    @Test
    public void shouldOpen() {
        // In order for Sauce to be able to Securely access your application, we use Sauce Connect
        driver.get("http://localhost:3000");
        assertEquals("React App", driver.getTitle());
    }
}
