package com.saucedemo.selenium;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
public class SeleniumTestBase {

    public RemoteWebDriver driver;

    @RegisterExtension
    public SeleniumTestBase.SauceTestWatcher watcher = new SeleniumTestBase.SauceTestWatcher();

    public void basicSetup(TestInfo testInfo, MutableCapabilities options) {
        Map<String, Object> sauceOptions = (Map<String, Object>) options.getCapability("sauce:options");

        if (sauceOptions == null) {
            sauceOptions = new HashMap<>();
        }

        sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.put("name", testInfo.getDisplayName());
        options.setCapability("sauce:options", sauceOptions);

        watcher.setName(testInfo.getDisplayName());

        URL url;
        try {
            url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        driver = new RemoteWebDriver(url, options);
        watcher.setId(driver.getSessionId());
    }

    public void basicSetup(TestInfo testInfo) {
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName("Windows 10");
        options.setBrowserVersion("latest");

        basicSetup(testInfo, options);
    }

    public class SauceTestWatcher implements TestWatcher {
        private String name;
        private String id;

        @Override
        public void testSuccessful(ExtensionContext context) {
            printResults();
            driver.executeScript("sauce:job-result=passed");
            driver.quit();
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            printResults();
            driver.executeScript("sauce:job-result=failed");
            driver.quit();
        }

        public void printResults() {
            String sauceReporter = String.format("SauceOnDemandSessionID=%s job-name=%s", id, name);
            String sauceTestLink = String.format("Test Job Link: https://app.saucelabs.com/tests/%s", id);
            System.out.print(sauceReporter + "\n" + sauceTestLink + "\n");
        }

        public void setName(String displayName) {
            this.name = displayName;
        }

        public String getName() {
            return name;
        }

        public void setId(SessionId sessionId) {
            this.id = String.valueOf(sessionId);
        }
    }
}
