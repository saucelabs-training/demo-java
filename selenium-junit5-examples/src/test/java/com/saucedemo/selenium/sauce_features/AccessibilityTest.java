package com.saucedemo.selenium.sauce_features;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.saucedemo.selenium.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class AccessibilityTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @DisplayName("Deque Axe Test With Selenium Not html")
  @Test
  public void accessibilityTest() {
    driver.navigate().to("https://www.saucedemo.com");

    AxeBuilder axeBuilder;
    axeBuilder = new AxeBuilder();
    Results accessibilityResults = axeBuilder.analyze(driver);

    Assertions.assertEquals(3, accessibilityResults.getViolations().size());
  }
}
