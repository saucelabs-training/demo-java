package com.saucelabs.selenium;

import com.saucelabs.selenium.TestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.HasFullPageScreenshot;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.Augmenter;

public class ViewPageFirefoxTest extends TestBase {
  public static Path directory;

  @BeforeEach
  public void setup(TestInfo testInfo) throws IOException {
    startFirefoxSession(testInfo);
    driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
    directory = Files.createTempDirectory("firefox-");
    directory.toFile().deleteOnExit();
  }

  @Test
  public void printPage() throws IOException {
    Pdf print = ((PrintsPage) driver).print(new PrintOptions());

    Path printPage = Paths.get(directory + "PrintPage.pdf");
    Files.write(printPage, OutputType.BYTES.convertFromBase64Png(print.getContent()));
    Assertions.assertTrue(printPage.toFile().exists());
  }

  @Test
  public void takeScreenshot() throws IOException {
    byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

    Path screenshot = Paths.get(directory + "Screenshot.png");
    Files.write(screenshot, screenshotAs);
    Assertions.assertTrue(screenshot.toFile().exists());
  }

  @Test
  public void takeFullPageScreenshot() throws IOException {
    driver = new Augmenter().augment(driver);
    byte[] screenshotAs =
        ((HasFullPageScreenshot) driver).getFullPageScreenshotAs(OutputType.BYTES);

    Path fullPageScreenshot = Paths.get(directory + "FullPage.png");
    Files.write(fullPageScreenshot, screenshotAs);
    Assertions.assertTrue(fullPageScreenshot.toFile().exists());
  }
}
