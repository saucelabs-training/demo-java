package com.saucedemo;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;

public class TestConfigurations {
  static final String ANDROID_APP_URL =
      "https://github.com/saucelabs/my-demo-app-android/releases/download/2.1.0/mda-2.1.0-24.apk";
  static final String SAUCE_CLOUD = System.getProperty("sauce.cloud", "vdc");
  static final String BUILD_TIME = String.valueOf(System.currentTimeMillis());

  // Best practice would put these constants in a config file and dynamically pull them.
  public static Capabilities getCapabilities(TestInfo testInfo) {
    if (SAUCE_CLOUD.equalsIgnoreCase("vdc")) {
      return androidAppVDC(testInfo);
    } else if (SAUCE_CLOUD.equalsIgnoreCase("rdc")) {
      return androidAppRDC(testInfo);
    } else {
      throw new RuntimeException("Must set sauce.cloud property to vdc or rdc");
    }
  }

  // Generate these capabilities with Platform Configurator:
  // https://app.saucelabs.com/platform-configurator
  private static Capabilities androidAppRDC(TestInfo testInfo) {
    Map<String, Object> caps = new HashMap<>();
    caps.put("platformName", "Android");
    caps.put("appium:automationName", "UiAutomator2");
    caps.put("appium:app", ANDROID_APP_URL);
    caps.put("appium:deviceName", "Google.*");

    Map<String, Object> sauceOptions = new HashMap<>();
    sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    sauceOptions.put("appiumVersion", "latest");
    sauceOptions.put("name", testInfo.getDisplayName());
    sauceOptions.put("build", "Android App RDC: " + BUILD_TIME);
    caps.put("sauce:options", sauceOptions);

    return new MutableCapabilities(caps);
  }

  private static Capabilities androidAppVDC(TestInfo testInfo) {
    Map<String, Object> caps = new HashMap<>();
    caps.put("platformName", "Android");
    caps.put("appium:automationName", "UiAutomator2");
    caps.put("appium:app", ANDROID_APP_URL);
    caps.put("appium:deviceName", "Android GoogleAPI Emulator");
    caps.put("appium:platformVersion", "current_major");
    caps.put("appium:appPackage", "com.saucelabs.mydemoapp.android");

    Map<String, Object> sauceOptions = new HashMap<>();
    sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    sauceOptions.put("name", testInfo.getDisplayName());
    sauceOptions.put("build", "Android App VDC: " + BUILD_TIME);
    caps.put("sauce:options", sauceOptions);

    return new MutableCapabilities(caps);
  }
}
