package com.saucedemo;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class PerformanceExampleTests {
    @Test
    public void simplePerformanceTest() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setExtendedDebugging(true);
        sauceOptions.setCapturePerformance(true);

        SauceSession session = new SauceSession(sauceOptions);
        WebDriver driver = session.start();
        driver.get("https://www.saucedemo.com");
    }
}
