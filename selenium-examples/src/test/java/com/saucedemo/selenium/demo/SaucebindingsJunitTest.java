package com.saucedemo.selenium.demo;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * saucebindings.junit5 package provides a default superclass to handle set up and tear down
 *
 * @see <a href="https://opensource.saucelabs.com/sauce_bindings/docs/test-runners/">Sauce Binding Test Runners</a>
 *     for more information
 */
public class SaucebindingsJunitTest extends SauceBaseTest {

    @DisplayName("saucebindings-junit5 package example")
    @Test
    public void saucebindingsJunit5Test() {
        driver.navigate().to("https://www.saucedemo.com");
        Assertions.assertEquals("Swag Labs", driver.getTitle());
    }
}
