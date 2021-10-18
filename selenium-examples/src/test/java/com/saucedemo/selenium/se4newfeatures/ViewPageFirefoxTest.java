package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.HasFullPageScreenshot;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewPageFirefoxTest extends SauceBaseTest {
    public final static String directory = "src/test/screenshots/";

    public SauceOptions createSauceOptions() {
        return SauceOptions.firefox()
                .setGeckodriverVersion("0.30.0")
                .setBrowserVersion("beta")
                .build();
    }

    @BeforeEach
    public void navigate() {
        driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
    }

    @Test
    public void printPage() throws IOException {

        Path printPage = Paths.get(directory + "PrintPageFirefox.pdf");
        Pdf print = driver.print(new PrintOptions());

        Files.write(printPage, OutputType.BYTES.convertFromBase64Png(print.getContent()));
    }

    @Test
    public void takeScreenshot() throws IOException {

        Path screenshot = Paths.get(directory + "TakeScreenshotFirefox.png");
        byte[] screenshotAs = driver.getScreenshotAs(OutputType.BYTES);

        Files.write(screenshot, screenshotAs);
    }

    @Test
    public void takeFullPageScreenshot() throws IOException {
        WebDriver augmentedDriver = new Augmenter().augment(driver);
        File file = ((HasFullPageScreenshot) augmentedDriver).getFullPageScreenshotAs(OutputType.FILE);

        Path fullPageScreenshot = Paths.get(directory + "TakeFullPageScreenshotFirefox.png");
        Files.move(file.toPath(), fullPageScreenshot);
    }
}
