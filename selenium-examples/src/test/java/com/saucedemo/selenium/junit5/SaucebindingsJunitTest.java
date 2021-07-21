package com.saucedemo.selenium.junit5;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * saucebindings.junit5 package provides a default superclass to handle set up and tear down
 * @see <a href="https://opensource.saucelabs.com/sauce_bindings/docs/test-runners/">Sauce Binding Test Runners</a>
 * for more information
 */
public class SaucebindingsJunitTest extends SauceBaseTest {
    /**
     * @DisplayName is a JUnit 5 annotation that defines test case name.
     */
    @DisplayName("saucebindings-junit5 package example")
    @Test
    public void SaucebindingsJunit5Test() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        Assertions.assertEquals("Swag Labs", driver.getTitle());
    }
}
