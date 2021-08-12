package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.Prerun;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for Basic Authentication.
 */
public class WindowsAuthentication {

    private SauceSession session;
    private SauceOptions sauceOptions;

    @After
    public void tearDown() {
        session.stop(true);
    }

    @Test
    public void basicAuthTest() {
        sauceOptions = new SauceOptions();
        sauceOptions.setPlatformName(SaucePlatform.WINDOWS_10);

        session = new SauceSession(sauceOptions);
        RemoteWebDriver driver = session.start();
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        WebElement content = driver.findElement(By.id("content"));

        assertTrue(content.isDisplayed());
    }

    @Test
    @Ignore("doesn't work")
    public void autoItScriptTest() {
        //Good AutoIt docs: https://support.saucelabs.com/hc/en-us/articles/360049978374-Sample-AutoIT-Example-to-Handle-Integrated-Windows-Authentication-Dialog-IWA-
        sauceOptions = new SauceOptions();

        Map<Prerun, Object> prerun = new HashMap<>();
        prerun.put(Prerun.EXECUTABLE, "sauce-storage:login.zip");
        prerun.put(Prerun.ARGS, "--silent");
        prerun.put(Prerun.ARGS, "-a");
        prerun.put(Prerun.ARGS, "-q");
        prerun.put(Prerun.BACKGROUND, true);
        sauceOptions.sauce().setPrerun(prerun);

        session = new SauceSession(sauceOptions);
        RemoteWebDriver driver = session.start();
        driver.get("http://the-internet.herokuapp.com/basic_auth");

        fail();
    }
}
