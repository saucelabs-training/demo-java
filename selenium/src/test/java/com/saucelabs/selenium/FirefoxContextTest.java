package com.saucelabs.selenium;

import com.saucelabs.selenium.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxCommandContext;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.HasContext;
import org.openqa.selenium.remote.Augmenter;

public class FirefoxContextTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.addPreference("intl.accept_languages", "de-DE");
    startSession(testInfo, firefoxOptions);
  }

  @Test
  public void changePrefs() {
    driver.get("https://www.google.com");

    String lang1 =
        driver
            .findElement(By.id("gws-output-pages-elements-homepage_additional_languages__als"))
            .getText();
    Assertions.assertTrue(lang1.contains("angeboten auf"));

    WebDriver augmentedDriver = new Augmenter().augment(driver);
    ((HasContext) augmentedDriver).setContext(FirefoxCommandContext.CHROME);

    ((JavascriptExecutor) driver)
        .executeScript("Services.prefs.setStringPref('intl.accept_languages', 'es-ES')");

    ((HasContext) augmentedDriver).setContext(FirefoxCommandContext.CONTENT);
    driver.navigate().refresh();

    String lang2 =
        driver
            .findElement(By.id("gws-output-pages-elements-homepage_additional_languages__als"))
            .getText();
    Assertions.assertTrue(lang2.contains("Ofrecido por"));
  }
}
