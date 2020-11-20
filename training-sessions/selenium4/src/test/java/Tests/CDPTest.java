package Tests;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v86.network.Network;
import org.openqa.selenium.devtools.v86.network.model.BlockedReason;
import org.openqa.selenium.devtools.v86.network.model.Headers;
import org.openqa.selenium.devtools.v86.network.model.ResourceType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.devtools.v86.network.Network.*;

// https://github.com/diemol/selenium-4-demo/blob/master/src/test/java/CDPTest.java

public class CDPTest {
    private ChromeDriver chromeDriver;
    private DevTools devTools;

    @Before
    public void setUp() {
        chromeDriver = new ChromeDriver();
        devTools = chromeDriver.getDevTools();
        devTools.createSession();
    }

    @Test
    public void blockUrls() throws InterruptedException {
        // Network enabled
        devTools.send(enable(empty(), empty(), empty()));

        // Block urls that have png and css
        devTools.send(setBlockedURLs(ImmutableList.of("*.css", "*.png")));

        // Listening to events and check that the urls are actually blocked
        devTools.addListener(Network.loadingFailed(), loadingFailed -> {
            if (loadingFailed.getType().equals(ResourceType.STYLESHEET) ||
                    loadingFailed.getType().equals(ResourceType.IMAGE)) {
                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.INSPECTOR);
            }
        });

        chromeDriver.get("https://www.diemol.com/selenium-4-demo/relative-locators-demo.html");
        // Thread.sleep only meant for demo purposes!
        Thread.sleep(5000);

        // Disabling network interception and reloading the site
        devTools.send(Network.disable());
        chromeDriver.get("https://www.diemol.com/selenium-4-demo/relative-locators-demo.html");
        // Thread.sleep only meant for demo purposes!
        Thread.sleep(5000);
    }

    @Test
    public void addExtraHeaders() throws InterruptedException {
        chromeDriver.get("https://manytools.org/http-html-text/http-request-headers/");
        // Thread.sleep only meant for demo purposes!
        Thread.sleep(10000);

        devTools.send(enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.send(setExtraHTTPHeaders(new Headers(ImmutableMap.of("meetup", "TestingUY"))));

        devTools.addListener(loadingFailed(), loadingFailed -> {
            if (loadingFailed.getType().equals(ResourceType.STYLESHEET)) {
                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.INSPECTOR);
            }
        });

        devTools.addListener(requestWillBeSent(),
                requestWillBeSent ->
                        assertEquals(requestWillBeSent.getRequest().getHeaders().get("meetup"), "TestingUY"));

        devTools.addListener(dataReceived(),
                dataReceived -> Assert.assertNotNull(dataReceived.getRequestId()));

        chromeDriver.get("https://manytools.org/http-html-text/http-request-headers/");
        // Thread.sleep only meant for demo purposes!
        Thread.sleep(20000);
    }

    @Test
    public void getPageScreenshot() throws IOException {
        chromeDriver.get("https://opensource.saucelabs.com/");
        Map<String, Object> result = chromeDriver.executeCdpCommand("Page.captureScreenshot", new HashMap<>());
        String data = (String) result.get("data");
        byte[] image = Base64.getDecoder().decode((data));
        Files.write(new File("screenshotFileName.png").toPath(), image);
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
        Files.write(new File("fullPageScreenshotFileName.png").toPath(), image);
    }

    @Test
    public void emulateTimezoneTest() throws InterruptedException {
        Map<String, Object> timezoneInfo = new HashMap<>();
        timezoneInfo.put("timezoneId", "America/Montevideo");

        devTools.send(enable(of(100000000), empty(), empty()));
        chromeDriver.executeCdpCommand("Emulation.setTimezoneOverride", timezoneInfo);
        chromeDriver.get("https://everytimezone.com/");

        // Thread.sleep only meant for demo purposes!
        Thread.sleep(20000);
    }

    @After
    public void tearDown() {
        chromeDriver.quit();
    }
}