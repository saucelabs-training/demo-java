package com.saucedemo.selenium.accessibility;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.saucedemo.selenium.SeleniumTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 * Accessibility Tests with Deque Library.
 */
public class DequeAxeTest extends SeleniumTestBase {

    @BeforeEach
    public void setup(TestInfo testInfo) {
        basicSetup(testInfo);
    }

    @DisplayName("Deque Axe Test With Selenium Not html")
    @Test
    public void accessibilityTest() {
        driver.navigate().to("https://www.saucedemo.com");

        AxeBuilder axeBuilder = new AxeBuilder();
        Results accessibilityResults = axeBuilder.analyze(driver);

        Assertions.assertEquals(3, accessibilityResults.getViolations().size());
    }
}
