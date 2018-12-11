package com.yourcompany.Tests;

import com.yourcompany.Pages.GuineaPigPage;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TimeoutErrorTest {

    WebDriver driver;
    String buildTag;

    String username = System.getenv("SAUCE_USERNAME");
    String accesskey = System.getenv("SAUCE_ACCESS_KEY");


    @Test
    public void timeoutErrorTest()
            throws InvalidElementStateException, MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // set desired capabilities to launch appropriate browser on Sauce
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Firefox");
        capabilities.setCapability(CapabilityType.VERSION, "latest");
        capabilities.setCapability(CapabilityType.PLATFORM, "Windows 10");
        capabilities.setCapability("name", "testThatThrowsTimeoutError");

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        // Launch remote browser and set it as the current thread
        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub"),
                capabilities);

        GuineaPigPage page = GuineaPigPage.visitPage(driver);
    }

}
