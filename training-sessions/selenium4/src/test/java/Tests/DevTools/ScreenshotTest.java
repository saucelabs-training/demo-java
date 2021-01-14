package Tests.DevTools;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ScreenshotTest {
    // https://github.com/diemol/selenium-4-demo/blob/master/src/test/java/CDPTest.java

    private ChromeDriver chromeDriver;
    private DevTools devTools;

    @Before
    public void setUp() {
        chromeDriver = new ChromeDriver();
        devTools = chromeDriver.getDevTools();
        devTools.createSession();
    }

    @Test
    public void getPageScreenshot() throws IOException {
        chromeDriver.get("https://opensource.saucelabs.com/");

        Map<String, Object> result = chromeDriver
                .executeCdpCommand("Page.captureScreenshot", new HashMap<>());
        String data = (String) result.get("data");
        byte[] image = Base64.getDecoder().decode((data));
        Files.write(new File("ViewportScreenshot.png").toPath(), image);
    }

    @Test
    public void getFullPageScreenshot() throws IOException {
        chromeDriver.get("https://opensource.saucelabs.com/");
        long width = (long) chromeDriver.executeScript("return document.body.scrollWidth");
        long height = (long) chromeDriver.executeScript("return document.body.scrollHeight");
        long scale = (long) chromeDriver.executeScript("return window.devicePixelRatio");

        HashMap<String, Object> setDeviceMetricsOverride = new HashMap<>();
        setDeviceMetricsOverride.put("deviceScaleFactor", scale);
        setDeviceMetricsOverride.put("mobile", false);
        setDeviceMetricsOverride.put("width", width);
        setDeviceMetricsOverride.put("height", height);
        chromeDriver.executeCdpCommand("Emulation.setDeviceMetricsOverride", setDeviceMetricsOverride);

        Map<String, Object> result = chromeDriver.executeCdpCommand("Page.captureScreenshot", new HashMap<>());
        String data = (String) result.get("data");
        byte[] image = Base64.getDecoder().decode((data));
        Files.write(new File("FullPageScreenshot.png").toPath(), image);
    }

    @After
    public void tearDown() {
        chromeDriver.quit();
    }
}