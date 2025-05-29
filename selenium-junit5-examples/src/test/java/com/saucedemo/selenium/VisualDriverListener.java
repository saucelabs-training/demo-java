package com.saucedemo.selenium;

import com.saucelabs.visual.CheckOptions;
import com.saucelabs.visual.VisualApi;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisualDriverListener implements WebDriverListener {
  private final VisualApi visual;
  private final CheckOptions checkOptions;

  public VisualDriverListener(WebDriver driver, TestInfo testInfo) {
    this.visual =
        new VisualApi.Builder(
                (RemoteWebDriver) driver,
                System.getenv("SAUCE_USERNAME"),
                System.getenv("SAUCE_ACCESS_KEY"))
            .withBuild(System.getProperty("build.name"))
            .build();
    ;
    checkOptions = new CheckOptions();
    checkOptions.setSuiteName(testInfo.getTestClass().map(Class::getName).orElse("UnknownSuite"));
    checkOptions.setTestName(testInfo.getDisplayName());
  }

  @Override
  public void afterTo(WebDriver.Navigation navigation, String url) {
    visual.sauceVisualCheck("Navigated to: " + url, checkOptions);
  }

  @Override
  public void afterClick(WebElement element) {
    visual.sauceVisualCheck("Clicked on: " + parseLocator(element), checkOptions);
  }

  @Override
  public void beforeQuit(WebDriver driver) {
    visual.sauceVisualCheck("Test Completed", checkOptions);
  }

  private String parseLocator(WebElement element) {
    if (element == null) {
      return "null";
    }

    String raw = element.toString();
    Pattern pattern = Pattern.compile("->\\s*(.+?):\\s*(.+?)]$");
    Matcher matcher = pattern.matcher(raw);

    if (matcher.find()) {
      String strategy = matcher.group(1); // e.g., "css selector"
      String value = matcher.group(2);    // e.g., "input[data-test='login-button']"
      return strategy + ": " + value;
    }

    return "unknown locator from: " + raw;
  }
}

