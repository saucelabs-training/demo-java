package com.saucedemo;

public class Constants {
    public static final String region = System.getProperty("region", "us");
    public static final String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com:443/wd/hub";
    public static final String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";

    public static final String SLEEP_BEFORE_SEC = System.getenv("SLEEP_BEFORE_SEC");
    public static final String SAUCE_URL_OVERRIDE = System.getenv("SAUCE_URL_OVERRIDE");
    public static final String INCLUDE_MAC = System.getenv("INCLUDE_MAC");
    public static final String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
    public static final String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");

}

