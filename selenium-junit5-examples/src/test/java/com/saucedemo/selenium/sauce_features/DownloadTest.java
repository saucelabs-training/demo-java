package com.saucedemo.selenium.sauce_features;

import com.saucedemo.selenium.TestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DownloadTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    ChromeOptions options = new ChromeOptions();
    options.setEnableDownloads(true);
    startSession(testInfo, options);
  }

  @DisplayName("Download File from Sauce")
  @Test
  public void downloadTest() throws IOException {
    List<String> fileNames = new ArrayList<>();
    fileNames.add("file_1.txt");
    fileNames.add("file_2.jpg");
    driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");
    driver.findElement(By.id("file-1")).click();
    driver.findElement(By.id("file-2")).click();
    new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(d -> ((HasDownloads) d).getDownloadableFiles().contains("file_2.jpg"));

    List<String> files = ((HasDownloads) driver).getDownloadableFiles();

    // Sorting them to avoid differences when comparing the files
    fileNames.sort(Comparator.naturalOrder());
    files.sort(Comparator.naturalOrder());

    Assertions.assertEquals(fileNames, files);
    String downloadableFile = files.get(0);
    Path targetDirectory = Files.createTempDirectory("download");

    ((HasDownloads) driver).downloadFile(downloadableFile, targetDirectory);

    String fileContent =
        String.join("", Files.readAllLines(targetDirectory.resolve(downloadableFile)));
    Assertions.assertEquals("Hello, World!", fileContent);
  }
}
