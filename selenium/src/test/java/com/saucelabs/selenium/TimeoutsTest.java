package com.saucelabs.selenium;

import com.saucelabs.selenium.TestBase;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public class TimeoutsTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @Test
  public void getTimoutValues() {
    WebDriver.Timeouts timeouts = driver.manage().timeouts();

    timeouts.pageLoadTimeout(Duration.ofSeconds(33));
    timeouts.implicitlyWait(Duration.ofMillis(333));
    timeouts.scriptTimeout(Duration.ofSeconds(33));
    timeouts.getPageLoadTimeout();
    // These getters do not exist in Selenium 3
    Assertions.assertEquals(Duration.ofSeconds(33), timeouts.getPageLoadTimeout());
    Assertions.assertEquals(Duration.ofMillis(333), timeouts.getImplicitWaitTimeout());
    Assertions.assertEquals(Duration.ofSeconds(33), timeouts.getScriptTimeout());
  }
}
