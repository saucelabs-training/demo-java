package com.saucedemo.selenium;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.HashMap;
import java.util.Map;

public class PerformanceTest extends SauceBaseTest {

    @Override
    public SauceOptions createSauceOptions() {
        return SauceOptions.chrome()
                .setExtendedDebugging()
                .setName("") // bug - https://github.com/saucelabs/sauce_bindings/issues/267
                .setCapturePerformance()
                .build();
    }

    @DisplayName("Performance Test Metrics")
    @Test
    public void swagLabsPerformance() {
        driver.get("https://www.saucedemo.com");

        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");

        Map<String, Object> perfMetrics = (Map<String, Object>) driver.executeScript("sauce:log", metrics);
        int loadTime = Integer.parseInt(perfMetrics.get("load").toString());
        Assertions.assertTrue(loadTime < 1500);
    }

    @DisplayName("Selective Performance Test Metrics")
    @Test
    public void swagLabsLoginPerformance() {
        driver.get("https://www.saucedemo.com");

        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");
        Map<String, Object> perfMetrics1 = (Map<String, Object>) driver.executeScript("sauce:log", metrics);


        // Disabling performance prevents previous metrics from being overridden
        driver.executeScript("sauce:performanceDisable");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Map<String, Object> perfMetrics2 = (Map<String, Object>) driver.executeScript("sauce:log", metrics);

        // Additionally, if metrics were captured for filling the form, load would be null
        Assertions.assertEquals(perfMetrics1.get("load"), perfMetrics2.get("load"));
    }



}
