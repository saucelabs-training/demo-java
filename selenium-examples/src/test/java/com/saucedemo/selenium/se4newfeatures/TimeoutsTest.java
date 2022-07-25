package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
public class TimeoutsTest extends SauceBaseTest {

    @Test
    public void getTimoutValues() {
        WebDriver.Timeouts timeouts = driver.manage().timeouts();

        timeouts.pageLoadTimeout(Duration.ofSeconds(33));
        timeouts.implicitlyWait(Duration.ofMillis(333));
        timeouts.scriptTimeout(Duration.ofSeconds(33));
        timeouts.getPageLoadTimeout();
        // These getters do not exist in Selenium 3
        Assertions.assertEquals(Duration.ofSeconds(33), timeouts.getPageLoadTimeout());
        Assertions.assertEquals(Duration.ofMillis(333), timeouts.getImplicitWaitTimeout());
        Assertions.assertEquals(Duration.ofSeconds(33), timeouts.getScriptTimeout());
    }
}
