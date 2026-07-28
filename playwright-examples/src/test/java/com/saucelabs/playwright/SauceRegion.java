package com.saucelabs.playwright;

/** Sauce Labs data centers reachable through the native Playwright WebSocket endpoint. */
public enum SauceRegion {
  US_WEST("us-west-1"),
  US_EAST("us-east-1"),
  EU_CENTRAL("eu-central-1"),
  APAC_SOUTHEAST("apac-southeast-1");

  private final String value;

  SauceRegion(String value) {
    this.value = value;
  }

  public String ondemandUrl() {
    return "https://ondemand." + value + ".saucelabs.com";
  }

  public String apiUrl() {
    return "https://api." + value + ".saucelabs.com";
  }

  public String testLink() {
    return value.equals("us-west-1")
        ? "https://app.saucelabs.com/tests/"
        : "https://app." + value + ".saucelabs.com/tests/";
  }
}
