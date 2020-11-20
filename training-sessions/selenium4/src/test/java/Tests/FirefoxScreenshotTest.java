package Tests;

import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FirefoxScreenshotTest {

    @Test
    public void fullPageScreenshot() throws IOException {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);

        driver.manage().window().setSize(new Dimension(600, 600));
        driver.navigate().to("https://www.saucedemo.com/inventory.html");
        File tempfile = driver.getFullPageScreenshotAs(OutputType.FILE);

        Files.move(tempfile.toPath(), Paths.get((new File("screenshot.png").getAbsolutePath())));

        driver.quit();
    }
}


