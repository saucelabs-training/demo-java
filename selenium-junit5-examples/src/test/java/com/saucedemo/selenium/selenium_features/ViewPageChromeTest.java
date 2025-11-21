package com.saucedemo.selenium.selenium_features;

import com.saucedemo.selenium.TestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ViewPageChromeTest extends TestBase {
  public static Path directory;

  @BeforeEach
  public void setup(TestInfo testInfo) throws IOException {
    startChromeSession(testInfo);
    driver.navigate().to("https://www.saucedemo.com/");
    Cookie cookie =
        new Cookie.Builder("session-username", "standard_user")
            .domain("www.saucedemo.com")
            .path("/")
            .build();
    driver.manage().addCookie(cookie);
    driver.navigate().to("https://www.saucedemo.com/inventory.html");
    directory = Files.createTempDirectory("chrome-");
    directory.toFile().deleteOnExit();
  }

  @Test
  public void takeScreenshot() throws IOException {
    byte[] screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

    Path screenshot = Paths.get(directory + "Screenshot.png");
    Files.write(screenshot, screenshotAs);
    Assertions.assertTrue(screenshot.toFile().exists());
  }
}
