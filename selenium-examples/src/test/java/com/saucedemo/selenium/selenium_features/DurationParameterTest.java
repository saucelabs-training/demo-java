package com.saucedemo.selenium.selenium_features;

import com.saucedemo.selenium.TestBase;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DurationParameterTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @DisplayName("Timeout integers still work but are deprecated")
  @Test
  public void timeoutIntegersDeprecated() {

    // Uses Seconds
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    // Uses Long / TimeUnit
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(555));
  }

  @DisplayName("Timeouts now use Duration instances")
  @Test
  public void timeoutUnitsDeprecated() {

    // Uses Seconds
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    // Uses Long / TimeoutUnit
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(555));
  }
}
