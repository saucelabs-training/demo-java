package com.yourcompany.Utils;

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
        String seleniumURI = "@ondemand.saucelabs.com:80";
        String seleniumPort = System.getenv("SELENIUM_PORT");
        String seleniumHost = System.getenv("SELENIUM_HOST");
        if (!doNotUseSauceConnectCmdRelay &&
                seleniumPort != null &&
                seleniumHost != null &&
                !seleniumHost.contentEquals("ondemand.saucelabs.com")) {
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
}
