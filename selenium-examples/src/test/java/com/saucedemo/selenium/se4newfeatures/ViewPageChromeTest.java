package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PrintOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ViewPageChromeTest extends SauceBaseTest {
    public final static String directory = "src/test/screenshots/";

    public SauceOptions createSauceOptions() {
        ChromeOptions options = new ChromeOptions();
        // note: headless only actually required for print page
        options.setHeadless(true);

        return SauceOptions.chrome(options).build();
    }

    @Test
    public void printPage() throws IOException {
        driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");

        Path printPage = Paths.get(directory + "PrintPageChrome.pdf");
        Pdf print = driver.print(new PrintOptions());

        Files.write(printPage, OutputType.BYTES.convertFromBase64Png(print.getContent()));
    }

    @Test
    public void takeScreenshot() throws IOException {
        driver.navigate().to("https://www.saucedemo.com/v1/inventory.html");
        byte[] screenshotAs = driver.getScreenshotAs(OutputType.BYTES);

        Path screenshot = Paths.get(directory + "TakeScreenshotChrome.png");
        Files.write(screenshot, screenshotAs);
    }
}
