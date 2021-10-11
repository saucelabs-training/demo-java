package com.saucedemo.selenium.demo;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * saucebindings.junit5 package provides a default superclass to handle set up and tear down
 *
 * @see <a href="https://opensource.saucelabs.com/sauce_bindings/test-runners/">Sauce Binding Test Runners</a>
 *     for more information
 */
public class SaucebindingsJunitTest extends SauceBaseTest {

    @DisplayName("Sauce Bindings Test Runner Navigation Example")
    @Test
    public void sauceBindingsTestRunnerTest() {
        driver.navigate().to("https://www.saucedemo.com");
        Assertions.assertEquals("Swag Labs", driver.getTitle());
    }
}
