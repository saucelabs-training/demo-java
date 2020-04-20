package com.yourcompany.Tests;

import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;

public class TestBase  extends TestNGBase {

    @Override
    public SauceOptions updateOptions(SauceOptions options) {
        if (System.getenv("START_TIME") != null) {
            options.setBuild("Build Time: " + System.getenv("START_TIME"));
        }

        String platform;
        if (System.getProperty("platform") != null) {
            platform = System.getProperty("platform");
        } else {
            platform = "default";
        }

        switch(platform) {
            case "windows_10_edge":
                options.setPlatformName(SaucePlatform.WINDOWS_10);
                options.setBrowserName(Browser.EDGE);
                break;
            case "mac_sierra_chrome":
                options.setPlatformName(SaucePlatform.MAC_SIERRA);
                options.setBrowserName(Browser.CHROME);
                break;
            case "windows_8_ff":
                options.setPlatformName(SaucePlatform.WINDOWS_8);
                options.setBrowserName(Browser.FIREFOX);
                break;
            case "windows_8_1_ie":
                options.setPlatformName(SaucePlatform.WINDOWS_8_1);
                options.setBrowserName(Browser.INTERNET_EXPLORER);
                break;
            case "mac_mojave_safari":
                options.setPlatformName(SaucePlatform.MAC_MOJAVE);
                options.setBrowserName(Browser.SAFARI);
                break;
            default:
                // accept Sauce defaults
                break;
        }

        return options;
    }
}
