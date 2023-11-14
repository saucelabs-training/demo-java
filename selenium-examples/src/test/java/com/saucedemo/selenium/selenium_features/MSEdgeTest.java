package com.saucedemo.selenium.selenium_features;

import com.saucedemo.selenium.TestBase;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeOptions;

// Selenium 3 did not support any direct options for Chromium Edge (like excludeSwitches)
public class MSEdgeTest extends TestBase {

  @BeforeEach
  public void createSauceOptions(TestInfo testInfo) {
    EdgeOptions options = new EdgeOptions();
    options.setExperimentalOption(
        "excludeSwitches", Collections.singletonList("disable-popup-blocking"));
    Map<String, Object> sauceOptions = defaultSauceOptions(testInfo);

    startSession(options, sauceOptions);
  }

  @Test
  public void edgeExecution() {
    driver.get("https://deliver.courseavenue.com/PopupTest.aspx");
    driver.findElement(By.cssSelector("input[type=submit]")).click();

    Assertions.assertEquals(1, driver.getWindowHandles().size());
  }
}
