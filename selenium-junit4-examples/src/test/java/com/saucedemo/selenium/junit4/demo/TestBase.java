package com.saucedemo.selenium.junit4.demo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
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

  @Rule public TestName testName = new TestName();
  @Rule public SauceTestWatcher watcher = new SauceTestWatcher();

  protected SessionId id;

  public void startChromeSession() {
    startSession(new ChromeOptions());
  }

  public void startFirefoxSession() {
    startSession(new FirefoxOptions());
  }

  public void startSession(Capabilities options) {
    startSession(options, new HashMap<>());
  }

  protected void startSession(Capabilities options, Map<String, Object> sauceOptions) {
    Objects.requireNonNull(options, "options cannot be null");
    Objects.requireNonNull(sauceOptions, "sauceOptions cannot be null");

    sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    sauceOptions.put("name", testName.getMethodName());
    sauceOptions.put("screenResolution", "1440x900");
    sauceOptions.put("seleniumVersion", "4.22.0");
    ((MutableCapabilities) options).setCapability("sauce:options", sauceOptions);
    ((AbstractDriverOptions<?>) options).setPlatformName("Windows 11");
    URL url;
    try {
      url = new URL("https://ondemand.us-west-1.saucelabs.com/wd/hub");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    driver = new RemoteWebDriver(url, options);
    this.id = ((RemoteWebDriver) driver).getSessionId();
  }

  public class SauceTestWatcher extends TestWatcher {
    @Override
    public void succeeded(Description description) {
      printResults();
      try {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        driver.quit();
      } catch (Exception e) {
        System.out.println("problem with using driver: " + e);
      }
    }

    @Override
    public void failed(Throwable failure, Description description) {
      printResults();

      try {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
        driver.quit();
      } catch (Exception exception) {
        System.out.println("problem with using driver: " + exception);
      }
    }

    public void printResults() {
      String sauceReporter = String.format("SauceOnDemandSessionID=%s job-name=%s", id, testName);
      String sauceTestLink = String.format("Test Job Link: https://app.saucelabs.com/tests/%s", id);
      System.out.print(sauceReporter + "\n" + sauceTestLink + "\n");
    }
  }
}
