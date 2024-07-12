package com.saucedemo.pages;


import static com.saucedemo.Constants.WEB_URL;

import java.util.Map;
import org.openqa.selenium.remote.RemoteWebDriver;

/** All page objects inherit from the base page. */
public abstract class AbstractBasePage {
  protected final RemoteWebDriver driver;

  public AbstractBasePage(RemoteWebDriver driver) {
    this.driver = driver;
  }

  public RemoteWebDriver getDriver() {
    return this.driver;
  }

  /** Executes a visual test. */
  public final void takeSnapshot() {
    driver.executeScript("/*@visual.snapshot*/", this.getClass().getSimpleName());
  }

  public void visit() {
    driver.get(WEB_URL + getPagePart());
  }

  public abstract String getPagePart();

  /**
   * Screener uses this JavaScript to provide results of visual snapshot.
   *
   * @return Map of visual results
   */
  @SuppressWarnings("unchecked")
  public Map<String, Object> getVisualResults() {
    return (Map<String, Object>) driver.executeScript("/*@visual.end*/");
  }
}
