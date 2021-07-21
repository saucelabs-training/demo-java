package com.saucedemo.selenium.junit4.demo;

import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SauceBindingsJunit4Test extends SauceBaseTest {
    @Test
    public void correctTitle() {
        driver.get("https://www.saucedemo.com");
        assertEquals("Swag Labs", driver.getTitle());
    }
}
