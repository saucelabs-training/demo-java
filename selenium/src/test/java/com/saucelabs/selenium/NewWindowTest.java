package com.saucelabs.selenium;

import com.saucelabs.selenium.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;

public class NewWindowTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @Test
  public void secondWindow() {
    driver.switchTo().newWindow(WindowType.WINDOW);
    driver.manage().window().setPosition(new Point(100, 400));

    Assertions.assertEquals(2, driver.getWindowHandles().toArray().length);
  }

  @Test
  public void secondTab() {
    driver.switchTo().newWindow(WindowType.TAB);

    Assertions.assertEquals(2, driver.getWindowHandles().toArray().length);
  }
}
