package com.saucedemo.accessibility;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Test;

public class SauceBindingsExampleTest extends SauceBaseTest {

    @Test
    public void sauceDemoAccessibility() {
        driver.navigate().to("https://www.saucedemo.com");
        session.getAccessibilityResults();
    }

    @Test
    public void abcdcomputechTest() {
        driver.navigate().to("http://abcdcomputech.dequecloud.com");
        session.getAccessibilityResults();
    }
}
