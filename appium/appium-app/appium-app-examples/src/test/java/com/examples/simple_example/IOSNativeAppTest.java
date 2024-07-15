package com.examples.simple_example;

import static com.helpers.Constants.SAUCE_EU_URL;
import static com.helpers.Constants.SAUCE_US_URL;
import static com.helpers.Constants.rdc;
import static com.helpers.Constants.region;
import static org.assertj.core.api.Assertions.assertThat;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IOSNativeAppTest {

  @Rule public TestName name = new TestName();
  // This rule allows us to set test status with Junit
  @Rule public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();
  By productsScreenLocator = AppiumBy.accessibilityId("Products");
  By sortButtonLocator = AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`name == \"Button\"`]");
  By sortModalLocator = AppiumBy.accessibilityId("Sort by:");
  private IOSDriver driver;

  @Before
  public void setUp() throws MalformedURLException {
    System.out.println("Sauce iOS Native App  - Before hook");

    MutableCapabilities capabilities = new MutableCapabilities();
    MutableCapabilities sauceOptions = new MutableCapabilities();
    URL url;
    String appName;

    switch (region) {
      case "us":
        url = new URL(SAUCE_US_URL);
        break;
      case "eu":
      default:
        url = new URL(SAUCE_EU_URL);
        break;
    }

    // For all capabilities please check
    // https://appium.io/docs/en/2.0/guides/caps/
    // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
    // to find the simulators/real device names, OS versions and appium versions you can use for
    // your testings
    capabilities.setCapability("platformName", "iOS");
    capabilities.setCapability("appium:automationName", "XCuiTest");
    if (rdc.equals("true")) {
      // Allocate any available iPhone device with version 14
      capabilities.setCapability("appium:deviceName", "iPhone.*");
      appName = "SauceLabs-Demo-App.ipa";
      sauceOptions.setCapability("resigningEnabled", true);
      sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
    } else {
      capabilities.setCapability("appium:deviceName", "iPhone 11 Simulator");
      appName = "SauceLabs-Demo-App.Simulator.zip";
    }
    capabilities.setCapability("appium:app", "storage:filename=" + appName);
    capabilities.setCapability("appium:platformVersion", "14");
    sauceOptions.setCapability("name", name.getMethodName());
    sauceOptions.setCapability("build", "myApp-job-1");
    List<String> tags = Arrays.asList("sauceDemo_ios", "iOS", "Demo");
    sauceOptions.setCapability("tags", tags);
    sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    capabilities.setCapability("sauce:options", sauceOptions);

    try {
      driver = new IOSDriver(url, capabilities);
    } catch (Exception e) {
      System.out.println("Error to create iOS Driver: " + e.getMessage());
    }

    // Setting the driver so that we can report results
    resultReportingTestWatcher.setDriver(driver);
  }

  @Test
  public void verifyPromptSortModal() {

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(productsScreenLocator));

    driver.findElement(sortButtonLocator).click();

    // Verify the sort modal is displayed on screen
    assertThat(isDisplayed(sortModalLocator, 5)).as("Verify sort modal is displayed").isTrue();
  }

  public Boolean isDisplayed(By locator, long timeoutInSeconds) {
    try {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    } catch (org.openqa.selenium.TimeoutException exception) {
      return false;
    }
    return true;
  }
}
