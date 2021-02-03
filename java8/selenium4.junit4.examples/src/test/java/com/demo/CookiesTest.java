package com.demo;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;

import java.util.HashMap;
import java.util.Optional;

import static org.openqa.selenium.devtools.v86.network.Network.*;

public class CookiesTest {

    @Test
    public void manageCookies()  {
        ChromeDriver chromeDriver = new ChromeDriver();
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();

        chromeDriver.get("https://titusfortner.com/examples/cookies.html");

        // Network enabled
        devTools.send(new Command<>("Network.enable", new HashMap<>()));

        // Add Cookies
        devTools.send(createCookie("foo", "bar"));
        devTools.send(createCookie("bar", "foo"));
        devTools.send(createCookie("foobar", "barfoo"));
        chromeDriver.findElement(By.tagName("button")).click();

        String values = chromeDriver.findElement(By.id("results")).getText();
        Assert.assertEquals(3, values.length() - values.replaceAll("=","").length());

        // Delete Cookie
        devTools.send(deleteCookies("bar", Optional.empty(),
                Optional.of("titusfortner.com"), Optional.empty()));
        chromeDriver.findElement(By.tagName("button")).click();
        String values2 = chromeDriver.findElement(By.id("results")).getText();
        Assert.assertEquals(2, values2.length() - values2.replaceAll("=","").length());

        // Delete All Cookies
        devTools.send(clearBrowserCookies());
        chromeDriver.findElement(By.tagName("button")).click();
        String values3 = chromeDriver.findElement(By.id("results")).getText();
        Assert.assertFalse(values3.contains("="));

        chromeDriver.quit();
    }

    public Command<Boolean> createCookie(String name, String value) {
        return setCookie(name, value, Optional.empty(),
                Optional.of("titusfortner.com"), Optional.empty(),
                Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());
    }

}
