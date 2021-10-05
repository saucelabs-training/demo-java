package com.saucelabs.selenium3.changes;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
public class DurationParameterTest extends SauceBaseTest {

    @Test
    public void timeoutUnit() {
        // Uses Seconds
        WebDriverWait wait = new WebDriverWait(driver,5);

        // Uses Long / TimeUnit
        driver.manage().timeouts().implicitlyWait(555, TimeUnit.MILLISECONDS);
    }
}
