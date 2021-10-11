package com.saucedemo.selenium.accessibility;

import com.deque.html.axecore.results.Results;
import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Accessibility Tests with Sauce Bindings.
 */
public class SauceBindingsTest extends SauceBaseTest {

    @DisplayName("Accessibility of Swag Labs")
    @Test
    public void sauceDemoAccessibility() {
        driver.navigate().to("https://www.saucedemo.com");
        Results results = session.getAccessibilityResults();
        Assertions.assertEquals(3, results.getViolations().size());
    }

    @DisplayName("Accessibility of abcdcomputech")
    @Test
    public void abcdcomputechTest() {
        driver.navigate().to("http://abcdcomputech.dequecloud.com");
        Results results = session.getAccessibilityResults();
        Assertions.assertEquals(7, results.getViolations().size());
    }
}
