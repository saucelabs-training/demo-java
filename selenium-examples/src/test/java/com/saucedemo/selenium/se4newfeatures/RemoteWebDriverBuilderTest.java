package com.saucedemo.selenium.se4newfeatures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.HasFullPageScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
public class RemoteWebDriverBuilderTest {

    /**
     * RemoteWebDriver builder gives you a few great things off the bat:
     * 1. Allows you to easily set Connection and Read Timeouts
     * 2. Automatically applies augmentation for casting to valid interfaces
     * 3. Keeps Browser Options and Sauce Options separate
     * 4. Address values are Strings not URL, which is just easier
     */
    @DisplayName("Use RemoteWebDriverBuilder class")
    @Test
    public void webDriverBuilder(TestInfo testInfo) {
        FirefoxOptions browserOptions = new FirefoxOptions();

        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("latest");
        browserOptions.setAcceptInsecureCerts(true);
        browserOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);

        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("name", testInfo.getDisplayName());
        sauceOptions.put("build", System.getenv("BUILD_NAME") + ": " + System.getenv("BUILD_NUMBER"));
        sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        ClientConfig config = ClientConfig.defaultConfig()
                .readTimeout(Duration.ofMinutes(3));

        WebDriver driver = RemoteWebDriver.builder()
                .oneOf(browserOptions)
                .setCapability("sauce:options", sauceOptions)
                .address("https://ondemand.us-west-1.saucelabs.com/wd/hub")
                .config(config)
                .build();

        ((HasFullPageScreenshot) driver).getFullPageScreenshotAs(OutputType.FILE);

        driver.quit();
    }
}
