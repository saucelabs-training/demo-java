package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Collections;

public class MSEdgeTest extends SauceBaseTest {

    public SauceOptions createSauceOptions() {
        EdgeOptions options = new EdgeOptions();

        // Selenium 3 did not support any direct options for Chromium Edge
        // Selenium 4 allows setting all compliant values on EdgeOptions

        options.setExperimentalOption("excludeSwitches",
                Collections.singletonList("disable-popup-blocking"));

        return SauceOptions.edge(options).build();
    }

    @Test
    public void edgeExecution() {
        driver.get("https://deliver.courseavenue.com/PopupTest.aspx");
        driver.findElement(By.cssSelector("input[type=submit]")).click();

        Assertions.assertEquals(1, driver.getWindowHandles().size());
        driver.quit();
    }
}
