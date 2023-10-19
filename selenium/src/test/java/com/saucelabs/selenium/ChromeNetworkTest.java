package com.saucelabs.selenium;

import com.saucelabs.selenium.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.openqa.selenium.remote.Augmenter;

public class ChromeNetworkTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @Test
  public void toggleOffline() {
    WebDriver augmentedDriver = new Augmenter().augment(driver);
    ChromiumNetworkConditions networkConditions = new ChromiumNetworkConditions();
    networkConditions.setOffline(true);
    ((HasNetworkConditions) augmentedDriver).setNetworkConditions(networkConditions);

    try {
      driver.get("https://www.saucedemo.com");
      Assertions.fail(
          "If Network is set to be offline, the previous line should throw an exception");
    } catch (WebDriverException ex) {
      ((HasNetworkConditions) augmentedDriver)
          .setNetworkConditions(new ChromiumNetworkConditions());
    }
    driver.get("https://www.saucedemo.com");
  }
}
