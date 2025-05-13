package com.saucedemo.playwright;

import com.microsoft.playwright.Page;
import com.saucelabs.bindings.SaucePlaywrightSession;
import com.saucelabs.extensions.SaucePlaywrightExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TestBase {
  SaucePlaywrightSession session;
  Page page;

  @RegisterExtension
  public SaucePlaywrightExtension sauceExtension = new SaucePlaywrightExtension();

  @BeforeEach
  public void setUp(SaucePlaywrightSession session, Page page) {
    this.session = session;
    this.page = page;
  }

  static {
    System.setProperty("sauce.build.name", "Playwright Sauce Demo");
    System.setProperty("sauce.build.number", String.valueOf(System.currentTimeMillis()));
  }
}
