package Tests.DevTools;

import com.google.common.net.MediaType;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.openqa.selenium.support.devtools.NetworkInterceptor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.openqa.selenium.OutputType.BYTES;

@SuppressWarnings("ALL")
public class NetworkInterceptTest {

    @Test
    public void replaceContent() throws IOException {
        ChromeDriver chromeDriver = new ChromeDriver();

        Path path = Paths.get("src/test/resources/kitten.jpeg");
        byte[] bytes = Files.readAllBytes(path);

        Route route = Route.matching(req -> req.toString().contains("jpg"))
                .to(() -> req -> {
                    return new HttpResponse()
                            .addHeader("Content-Type", MediaType.JPEG.toString())
                            .setContent(Contents.bytes(bytes));
                });

        try (NetworkInterceptor interceptor = new NetworkInterceptor(chromeDriver, route)) {
            chromeDriver.get("https://www.saucedemo.com/inventory.html");
        }

        Files.write(Paths.get("kittens.png"), ((TakesScreenshot) chromeDriver).getScreenshotAs(BYTES));

        Dimension imageSize = chromeDriver.findElements(By.tagName("img")).get(0).getSize();
        double ratio = (double) imageSize.width / (double) imageSize.height;
        Assert.assertEquals(1.50, ratio, .05);

        chromeDriver.quit();
    }
}
