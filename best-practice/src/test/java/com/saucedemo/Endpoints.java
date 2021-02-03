package com.saucedemo;

import java.net.MalformedURLException;
import java.net.URL;

public class Endpoints {
    public static URL getEmusimHub() throws MalformedURLException {
        String user = System.getenv("SAUCE_USERNAME");
        String key = System.getenv("SAUCE_ACCESS_KEY");
        return new URL("https://" + user + ":" + key +
                "@ondemand.saucelabs.com:443/wd/hub");
    }

    public static URL getScreenerHub() throws MalformedURLException {
        return new URL("https://hub.screener.io/wd/hub");
    }
}
