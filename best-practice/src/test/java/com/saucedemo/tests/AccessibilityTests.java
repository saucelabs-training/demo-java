package com.saucedemo.tests;

import com.deque.html.axecore.selenium.AxeBuilder;
import com.saucedemo.AbstractTestBase;
import com.saucedemo.pages.LoginPage;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Performance Tests.
 */
public class AccessibilityTests extends AbstractTestBase {
    private SauceSession session;

    @Before
    public void setup() {
        SauceOptions sauceOptions = SauceOptions.chrome()
                .setExtendedDebugging()
                .setName(testName.getMethodName())
                .setCapturePerformance()
                .build();
        sauceOptions.sauce().setBuild(buildName);

        session = new SauceSession(sauceOptions);
        driver = session.start();
    }

    @Test
    public void accessibilityTest() {
        driver.get("https://www.saucedemo.com/");

        // 3a. Get accessibility default results with frame support
        session.getAccessibilityResults();

        // 3b. Get accessibility default results without frame support
        // session.getAccessibilityResults(false);

        // 3c. Get accessibility default results with Deque Builder instance
        //     Options for configuring AxeBuilder are here: https://github.com/dequelabs/axe-core-maven-html#:~:text=axebuilder
        // AxeBuilder builder = new AxeBuilder();
        // session.getAccessibilityResults(builder);
    }
}
