package com.demo;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;

import java.util.HashMap;
import java.util.Optional;

import static org.openqa.selenium.devtools.v87.emulation.Emulation.setUserAgentOverride;

@SuppressWarnings("ALL")
public class UserAgentTest {

    @Test
    public void setLanguage() {
        ChromeDriver chromeDriver = new ChromeDriver();
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();
        devTools.send(new Command<>("Network.enable", new HashMap<>()));

        devTools.send(setUserAgentOverride("userAgent", Optional.of("sv"),
                Optional.empty(), Optional.empty()));

        chromeDriver.get("https://google.com");

        // This is returning "sv-US" but results are in Swedish; might not be fully implemented
        String language = chromeDriver.findElement(By.tagName("html")).getAttribute("lang");
        Assert.assertTrue(language.contains("sv-"));

        chromeDriver.quit();
    }


    @Ignore("Does not seem to work, yet")
    @Test
    public void setPlatformName() {
        ChromeDriver chromeDriver = new ChromeDriver();
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();

        devTools.send(new Command<>("Network.enable", new HashMap<>()));

        String platform = "iPhone";
        devTools.send(setUserAgentOverride("userAgent", Optional.empty(), Optional.of(platform), Optional.empty()));

        chromeDriver.get("https://google.com");

        String userAgent = (String) chromeDriver.executeScript("return navigator.userAgent;");
        Assert.assertEquals(platform, userAgent);

        chromeDriver.quit();
    }
}
