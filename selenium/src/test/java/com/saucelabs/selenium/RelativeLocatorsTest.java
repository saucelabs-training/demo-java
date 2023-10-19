package com.saucelabs.selenium;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import com.saucelabs.selenium.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class RelativeLocatorsTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @Test
  public void relativeLocators() {
    driver.get("https://www.diemol.com/selenium-4-demo/relative-locators-demo.html");

    WebElement element =
        driver.findElement(with(By.tagName("li")).toLeftOf(By.id("berlin")).below(By.id("warsaw")));

    Assertions.assertEquals("london", element.getAttribute("id"));

    ((JavascriptExecutor) driver).executeScript("arguments[0].style.filter='blur(8px)'", element);
  }
}
