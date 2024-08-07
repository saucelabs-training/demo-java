package com.saucedemo;

public class Constants {
  public static final String region = System.getProperty("region", "us");
  public static final String SAUCE_EU_URL =
      "https://ondemand.eu-central-1.saucelabs.com:443/wd/hub";
  public static final String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";
  public static final String WEB_URL = "https://www.saucedemo.com/";
}
