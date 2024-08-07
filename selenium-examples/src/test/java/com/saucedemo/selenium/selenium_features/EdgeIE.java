package com.saucedemo.selenium.selenium_features;

import com.saucedemo.selenium.TestBase;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.ie.InternetExplorerOptions;

public class EdgeIE extends TestBase {
  @BeforeEach
  public void createSauceOptions(TestInfo testInfo) {
    InternetExplorerOptions options = new InternetExplorerOptions();
    options.attachToEdgeChrome();
    options.setCapability("browserName", "microsoftedge");
    Map<String, Object> sauceOptions = defaultSauceOptions(testInfo);

    startSession(options, sauceOptions);
  }

  @Test
  public void ieMode() {
    driver.get("https://www.saucedemo.com");
  }
}
