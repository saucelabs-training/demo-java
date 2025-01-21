package com.saucedemo.selenium.selenium_features;

import com.saucedemo.selenium.TestBase;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.HasExtensions;
import org.openqa.selenium.remote.Augmenter;

public class FirefoxAddonTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startFirefoxSession(testInfo);
  }

  @Test
  public void addons() {
    WebDriver augmentedDriver = new Augmenter().augment(driver);
    String id =
        ((HasExtensions) augmentedDriver)
            .installExtension(Paths.get("src/test/resources/webextensions-selenium-example.xpi"));

    driver.get("https://www.saucedemo.com");
    WebElement injected = driver.findElement(By.id("webextensions-selenium-example"));
    Assertions.assertEquals(
        "Content injected by webextensions-selenium-example", injected.getText());

    ((HasExtensions) augmentedDriver).uninstallExtension(id);

    driver.navigate().refresh();
    Assertions.assertEquals(0, driver.findElements(By.id("webextensions-selenium-example")).size());
  }
}
