package com.saucedemo.playwright;

import com.microsoft.playwright.Page;
import com.saucelabs.playwright.SauceExtension;
import com.saucelabs.playwright.SauceSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public class TestBase {
  SauceSession session;
  Page page;

  @RegisterExtension public static SauceExtension sauceExtension = new SauceExtension();

  @BeforeEach
  public void setUp(SauceSession session, Page page) {
    this.session = session;
    this.page = page;
  }
}
