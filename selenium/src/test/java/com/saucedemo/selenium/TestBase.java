package com.saucedemo.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

public class TestBase {

  public WebDriver driver;

  @RegisterExtension public TestBase.SauceTestWatcher watcher = new TestBase.SauceTestWatcher();
  protected TestInfo testInfo;
  protected SessionId id;

  public void startChromeSession(TestInfo testInfo) {
    startSession(testInfo, new ChromeOptions());
  }

  public void startFirefoxSession(TestInfo testInfo) {
    startSession(testInfo, new FirefoxOptions());
  }

  public void startSession(TestInfo testInfo, Capabilities options) {
    startSession(testInfo, options, new HashMap<>());
  }

  protected void startSession(
      TestInfo testInfo, Capabilities options, Map<String, Object> sauceOptions) {
    Objects.requireNonNull(testInfo, "testInfo cannot be null");
    Objects.requireNonNull(options, "options cannot be null");
    Objects.requireNonNull(sauceOptions, "sauceOptions cannot be null");

    this.testInfo = testInfo;

    sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    sauceOptions.put("name", testInfo.getDisplayName());
    sauceOptions.put("seleniumVersion", "4.14.1");
    ((MutableCapabilities) options).setCapability("sauce:options", sauceOptions);
    ((AbstractDriverOptions<AbstractDriverOptions>) options).setPlatformName("Windows 11");

    URL url;
    try {
      url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    driver = new RemoteWebDriver(url, options);
    this.id = ((RemoteWebDriver) driver).getSessionId();
  }

  public class SauceTestWatcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
      printResults();
      try {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        driver.quit();
      } catch (Exception e) {
        System.out.println("problem with using driver: " + e);
      }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      printResults();

      try {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
        driver.quit();
      } catch (Exception e) {
        System.out.println("problem with using driver: " + e);
      }
    }

    public void printResults() {
      String sauceReporter =
          String.format("SauceOnDemandSessionID=%s job-name=%s", id, testInfo.getDisplayName());
      String sauceTestLink = String.format("Test Job Link: https://app.saucelabs.com/tests/%s", id);
      System.out.print(sauceReporter + "\n" + sauceTestLink + "\n");
    }
  }
}
