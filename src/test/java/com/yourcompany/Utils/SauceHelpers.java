package com.yourcompany.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
import org.openqa.selenium.remote.RemoteWebDriver;


import java.rmi.UnexpectedException;
import java.util.logging.Level;

/**
 * Created by mehmetgerceker on 12/9/15.
 */
public class SauceHelpers {

    /**
     * Will generate the URI that will be used to send commands to the Se instance.
     * If SauceConnect tunnel in use and not directed not to use it will use the SC command relay.
     *
     * @param doNotUseSauceConnectCmdRelay Even if available do not use the relay.
     * @return String formatted uri for Sauce Se commands.
     */
    public static String buildSauceUri(boolean doNotUseSauceConnectCmdRelay) {
        String seleniumURI = "@ondemand.saucelabs.com:443";
        String seleniumPort = System.getenv("SELENIUM_PORT");
        String seleniumHost = System.getenv("SELENIUM_HOST");
        if (!doNotUseSauceConnectCmdRelay &&
                seleniumPort != null &&
                !seleniumHost.equalsIgnoreCase("ondemand.saucelabs.com")) {
            //While running in CI, if Sauce Connect is running the SELENIUM_PORT env var will be set.
            //use SC relay port
            seleniumURI = String.format("@localhost:%s", seleniumPort);

        }
        return seleniumURI;
    }
    /**
     * Will generate the URI that will be used to send commands to the Se instance.
     * If SauceConnect tunnel in use it will use the SC command relay.
     * @return String formatted uri for Sauce Se commands.
     */
    public static String buildSauceUri() {
        return buildSauceUri(false);
    }
    /**
     * Adds/updates sauce tunnel id to desired capabilities in place
     * @param desiredCapabilities desired caps
     * @param tunnelId tunnel id
     */
    public static void addSauceConnectTunnelId(DesiredCapabilities desiredCapabilities, String tunnelId) {
        if (tunnelId == null || tunnelId.length() == 0) {
            tunnelId = System.getenv("TUNNEL_IDENTIFIER");
        }

        if (tunnelId != null && tunnelId.length() > 0){
            desiredCapabilities.setCapability("tunnel-identifier", tunnelId);
        }
    }

    /**
     * Adds/updates sauce tunnel id to desired capabilities from env in place
     * @param desiredCapabilities desired caps
     */
    public static void addSauceConnectTunnelId(DesiredCapabilities desiredCapabilities) {
        addSauceConnectTunnelId(desiredCapabilities, null);
    }

}
