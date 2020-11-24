package com.demo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.openqa.selenium.devtools.v86.network.Network.enable;

public class LocationTest {
    private ChromeDriver chromeDriver;
    private DevTools devTools;

    @Before
    public void setUp() {
        chromeDriver = new ChromeDriver();
        devTools = chromeDriver.getDevTools();
        devTools.createSession();
    }

    @Test
    public void setGeoLocation() throws InterruptedException {
        // https://www.selenium.dev/documentation/en/support_packages/chrome_devtools/

        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("latitude", 51.9194);
        coordinates.put("longitude", 19.1451);
        coordinates.put("accuracy", 1);

        chromeDriver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
        Thread.sleep(1000);
        chromeDriver.get("https://www.google.com/");
        chromeDriver.get("https://www.google.com/search?q=test");

        Assert.assertEquals("test - Szukaj w Google", chromeDriver.getTitle());
    }

    @Test
    public void emulateTimezoneTest()  {
        Map<String, Object> timezoneInfo = new HashMap<>();
        timezoneInfo.put("timezoneId", "Pacific/Honolulu");

        devTools.send(enable(of(100000000), empty(), empty()));
        chromeDriver.executeCdpCommand("Emulation.setTimezoneOverride", timezoneInfo);
        chromeDriver.get("https://whatismytimezone.com/");

        String articleText = chromeDriver.findElement(By.tagName("article")).getText();
        Assert.assertTrue(articleText.contains("GMT-1000"));
    }

    @After
    public void tearDown() {
        chromeDriver.quit();
    }
}
