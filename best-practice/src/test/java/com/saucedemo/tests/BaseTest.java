package com.saucedemo.tests;

import static com.saucedemo.Constants.SAUCE_EU_URL;
import static com.saucedemo.Constants.SAUCE_US_URL;
import static com.saucedemo.Constants.region;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseTest {
  @Parameterized.Parameter public String platform;

  @Parameterized.Parameter(1)
  public String browserDeviceName;

  @Parameterized.Parameter(2)
  public String browserPlatformVersion;

  @Parameterized.Parameter(3)
  public String platformName;

  @Rule public TestName name = new TestName();
  protected RemoteWebDriver driver;

  @Rule
  public TestRule watcher =
      new TestWatcher() {

        @Override
        protected void succeeded(Description description) {
          if (driver != null) {
            System.out.println("Test Passed!");
            driver.executeScript("sauce:job-result=passed");
            driver.quit();
          }
        }

        @Override
        public void failed(Throwable e, Description description) {
          if (driver != null) {
            System.out.println("Test Failed!");
            driver.executeScript("sauce:job-result=failed");
            driver.executeScript("sauce:context=" + e.getMessage());
            driver.quit();
          }
        }
      };

  @Parameterized.Parameters()
  public static Collection<Object[]> crossPlatformData() {
    return Arrays.asList(
        new Object[][] {
          {"desktop", "safari", "latest", "macOS 11.00"},
          {"desktop", "chrome", "latest-1", "macOS 13"},
          {"desktop", "firefox", "latest", "Windows 11"},
          {"desktop", "chrome", "latest", "Windows 10"}
        });
  }

  @Before
  public void setup() throws MalformedURLException {

    System.out.println("BeforeMethod hook");
    URL url;

    switch (region) {
      case "us":
        url = new URL(SAUCE_US_URL);
        break;
      case "eu":
      default:
        url = new URL(SAUCE_EU_URL);
        break;
    }

    boolean isBuildCap = false;
    MutableCapabilities caps = new MutableCapabilities();
    MutableCapabilities sauceOptions = new MutableCapabilities();

    switch (platform) {
      case "desktop":
        caps.setCapability("browserName", browserDeviceName);
        caps.setCapability("browserVersion", browserPlatformVersion);
        caps.setCapability("platformName", platformName);
        break;
      case "android":
        caps.setCapability("platformName", "android");
        caps.setCapability("appium:automationName", "UiAutomator2");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("appium:deviceName", browserDeviceName);
        caps.setCapability("appium:platformVersion", browserPlatformVersion);
        break;
      case "ios":
        caps.setCapability("platformName", "iOS");
        caps.setCapability("appium:automationName", "XCuiTest");
        caps.setCapability("browserName", "safari");
        caps.setCapability("appium:deviceName", browserDeviceName);
        caps.setCapability("appium:platformVersion", browserPlatformVersion);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + platform);
    }

    sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    sauceOptions.setCapability("name", name.getMethodName());

    if (!isBuildCap) { // handle build cap
      LocalDateTime dateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH");
      String buildLocal = "sauceDemo-" + dateTime.format(formatter);
      String buildVal = System.getenv("BUILD_TAG");
      sauceOptions.setCapability("build", buildVal == null ? buildLocal : buildVal);
    }

    caps.setCapability("sauce:options", sauceOptions);

    try {
      driver = new RemoteWebDriver(url, caps);

    } catch (Exception e) {
      System.out.println("*** Problem to create the driver " + e.getMessage());
      throw new RuntimeException(e);
    }
  }
}
