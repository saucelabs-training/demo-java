package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chromium.ChromiumNetworkConditions;
import org.openqa.selenium.chromium.HasNetworkConditions;
import org.openqa.selenium.remote.Augmenter;

public class ChromeNetworkTest extends SauceBaseTest {

    @Test
    public void toggleOffline() {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        ChromiumNetworkConditions networkConditions = new ChromiumNetworkConditions();
        networkConditions.setOffline(true);
        ((HasNetworkConditions) augmentedDriver).setNetworkConditions(networkConditions);

        try {
            driver.get("https://www.saucedemo.com");
            Assertions.fail("If Network is set to be offline, the previous line should throw an exception");
        } catch (WebDriverException ex) {
            ((HasNetworkConditions) augmentedDriver).setNetworkConditions(new ChromiumNetworkConditions());
        }
        driver.get("https://www.saucedemo.com");
    }
}
