package com.saucedemo.selenium.se4updates;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
public class DurationParameterTest extends SauceBaseTest {

    @DisplayName("Timeout integers still work but are deprecated")
    @Test
    public void timeoutIntegersDeprecated() {

        // Uses Seconds
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));

        // Uses Long / TimeUnit
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(555));
    }

    @DisplayName("Timeouts now use Duration instances")
    @Test
    public void timeoutUnitsDeprecated() {

        // Uses Seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Uses Long / TimeoutUnit
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(555));
    }
}
