package com.saucedemo.selenium.junit4.demo;

import static org.junit.Assert.assertEquals;

import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import org.junit.Test;

/** Example Test for using JUnit 4 Sauce Bindings jar. */
public class SauceBindingsJunit4Test extends SauceBaseTest {
  @Test
  public void correctTitle() {
    driver.get("https://www.saucedemo.com");
    assertEquals("Swag Labs", driver.getTitle());
  }
}
