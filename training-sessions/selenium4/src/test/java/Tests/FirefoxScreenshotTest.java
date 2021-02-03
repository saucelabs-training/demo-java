package Tests;

import org.junit.Test;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.openqa.selenium.OutputType.BYTES;

public class FirefoxScreenshotTest {

    @Test
    public void fullPageScreenshot() throws IOException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);

        driver.navigate().to("https://opensource.saucelabs.com");

        Path viewport = Paths.get("FirefoxViewportScreenshot.png");
        Files.write(viewport, ((TakesScreenshot) driver).getScreenshotAs(BYTES));

        Path fullPage = Paths.get("FirefoxFullPageScreenshot.png");
        Files.write(fullPage, driver.getFullPageScreenshotAs(BYTES));

        driver.quit();
    }
}


