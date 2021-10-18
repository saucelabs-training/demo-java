package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.HasExtensions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.LocalFileDetector;

import java.nio.file.Paths;

public class FirefoxAddonTest extends SauceBaseTest {

    public SauceOptions createSauceOptions() {
        return SauceOptions.firefox().build();
    }

    @Test
    public void addons() {
        driver.setFileDetector(new LocalFileDetector());
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        String id = ((HasExtensions) augmentedDriver).installExtension(Paths.get("src/test/resources/ninja_saucebot-1.0-an+fx.xpi"));

        driver.get("https://www.saucedemo.com");
        Assertions.assertTrue(driver.findElements(By.className("bot_column2")).size() > 0);
        ((HasExtensions) augmentedDriver).uninstallExtension(id);

        driver.navigate().refresh();
        Assertions.assertEquals(0, driver.findElements(By.className("bot_column2")).size());
    }
}
