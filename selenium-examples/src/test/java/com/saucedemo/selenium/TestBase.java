package com.saucedemo.selenium;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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

  static {
    String buildName = "Default Build Name";
    String buildNumber = String.valueOf(System.currentTimeMillis());
    System.setProperty("build.name", buildName + ": " + buildNumber);
  }

  public void startChromeSession(TestInfo testInfo) {
    startSession(testInfo, new ChromeOptions());
  }

  public void startFirefoxSession(TestInfo testInfo) {
    startSession(testInfo, new FirefoxOptions());
  }

  public void startSession(TestInfo testInfo, Capabilities options) {
    startSession(options, defaultSauceOptions(testInfo));
  }

  protected void startSession(Capabilities options, Map<String, Object> sauceOptions) {
    ((MutableCapabilities) options).setCapability("sauce:options", sauceOptions);
    if (options.getPlatformName() == null) {
      ((AbstractDriverOptions<AbstractDriverOptions>) options).setPlatformName("Windows 11");
    }

    URL url;
    try {
      url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    driver = new RemoteWebDriver(url, options);
    this.id = ((RemoteWebDriver) driver).getSessionId();
  }

  protected Map<String, Object> defaultSauceOptions(TestInfo testInfo) {
    this.testInfo = testInfo;

    Map<String, Object> options = new HashMap<>();
    options.put("username", System.getenv("SAUCE_USERNAME"));
    options.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    options.put("name", testInfo.getDisplayName());
    options.put("build", System.getProperty("build.name"));
    options.put("seleniumVersion", "4.15.0");
    return options;
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
