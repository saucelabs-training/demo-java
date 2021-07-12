package com.saucedemo.accessibility;

import com.deque.html.axecore.results.Results;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import org.junit.Assert;
import org.junit.Test;

public class SauceBindingsExampleTest extends SauceBaseTest {

    @Test
    public void accessibilityTest() {
        driver.navigate().to("https://www.saucedemo.com");
        Results accessibilityResults = session.getAccessibilityResults();
        Assert.assertEquals(3, accessibilityResults.getViolations().size());
    }
}
