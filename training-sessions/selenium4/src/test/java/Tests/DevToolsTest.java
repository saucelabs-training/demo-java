package Tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v84.log.Log;
import org.openqa.selenium.devtools.v84.network.Network;
import org.openqa.selenium.devtools.v84.network.model.Headers;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DevToolsTest {

    @Test
    public void geoLocation() throws InterruptedException {
        // https://www.selenium.dev/documentation/en/support_packages/chrome_devtools/

        ChromeDriver driver = new ChromeDriver();
        Map coordinates = new HashMap()
        {{
            put("latitude", 51.9194);
            put("longitude", 19.1451);
            put("accuracy", 1);
        }};
        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);
        Thread.sleep(1000);
        driver.get("https://www.google.com/");
        driver.get("https://www.google.com/search?q=test");

        Assert.assertEquals("test - Szukaj w Google", driver.getTitle());
        driver.quit();
    }

    @Test
    public void consoleLogs() {
        // https://crazyautomator.com/reading-console-logs-of-chrome-browser-using-selenium-4-chrome-devtools-and-java/
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), entry -> System.out.println(entry.getText()));
        driver.get("https://nhnb.github.io/console-log/console-log/demo.html");

        driver.quit();
    }

    @Test
    public void basicAuthentication() {
        // https://medium.com/@babu_27570/solved-windows-authentication-automation-with-webdriver-and-devtools-api-1894d1c2dd91
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));
        String auth = "admin:admin";

        String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());

        Map<String, Object> headers = new HashMap<String, Object>();
        headers.put("Authorization", "Basic "+encodeToString);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://the-internet.herokuapp.com/basic_auth");
        String text = driver.findElement(By.tagName("p")).getText();

        Assert.assertEquals("Congratulations! You must have the proper credentials.", text);

        driver.quit();
    }
}


