package com.saucedemo.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Performance Test.
 */
public class PerformanceTest extends SeleniumTestBase {

    @BeforeEach
    public void setup(TestInfo testInfo) {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("capturePerformance", true);
        sauceOptions.put("extendedDebugging", true);
        options.setCapability("sauce:options", sauceOptions);

        basicSetup(testInfo, options);
    }

    @DisplayName("Ensure all metrics within historical limits")
    @Test
    public void performanceAllMetrics() {
        driver.get("https://www.saucedemo.com");

        HashMap<String, Object> args = new HashMap<>();
        args.put("name", watcher.getName());
        Map<String, Object> performance = (Map<String, Object>) driver.executeScript("sauce:performance", args);

        Assertions.assertEquals("pass", performance.get("result"));
    }

    @DisplayName("Ensure provided metrics within historical limits")
    @Test
    public void performanceSpecificMetrics() {
        driver.get("https://www.saucedemo.com");

        HashMap<String, Object> args = new HashMap<>();
        args.put("name", watcher.getName());
        args.put("metrics", Arrays.asList("load", "firstContentfulPaint"));

        Map<String, Object> performance = (Map<String, Object>) driver.executeScript("sauce:performance", args);
        Assertions.assertEquals("pass", performance.get("result"));
    }

    @DisplayName("Get log of performance metrics from previous navigation")
    @Test
    public void performanceLog() {
        driver.get("https://www.saucedemo.com");

        HashMap<String, Object> metricsLog = new HashMap<>();
        metricsLog.put("type", "sauce:performance");
        Map<String, Object> metrics = (Map<String, Object>) driver.executeScript("sauce:log", metricsLog);

        Assertions.assertTrue((int) metrics.get("firstInteractive") < 5000 );
    }

    @DisplayName("Get jankiness metrics from previous navigation")
    @Test
    public void jankiness() {
        driver.get("https://www.saucedemo.com");

        Map<String, Object> metrics = (Map<String, Object>) driver.executeScript("sauce:jankinessCheck");

        Assertions.assertTrue((double) metrics.get("score") > 0.5 );
    }
}
