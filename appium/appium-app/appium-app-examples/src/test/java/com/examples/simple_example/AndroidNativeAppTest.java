package com.examples.simple_example;

import static com.helpers.Constants.SAUCE_EU_URL;
import static com.helpers.Constants.SAUCE_US_URL;
import static com.helpers.Constants.rdc;
import static com.helpers.Constants.region;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
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

/** Android Native App Tests */
public class AndroidNativeAppTest {

  @Rule public TestName name = new TestName();
  // This rule allows us to set test status with Junit
  @Rule public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();
  By productsScreenLocator = By.id("com.saucelabs.mydemoapp.android:id/productTV");
  By sortButtonLocator =
      AppiumBy.accessibilityId(
          "Shows current sorting order and displays available sorting options");
  By sortModalLocator = By.id("com.saucelabs.mydemoapp.android:id/sortTV");
  private AndroidDriver driver;

  @Before
  public void setup() throws MalformedURLException {
    System.out.println("Sauce Android Native App  - Before hook");
    MutableCapabilities capabilities = new MutableCapabilities();
    MutableCapabilities sauceOptions = new MutableCapabilities();
    URL url;

    if (region.equals("us")) {
      url = new URL(SAUCE_US_URL);
      System.out.println("Sauce REGION US");
    } else {
      url = new URL(SAUCE_EU_URL);
      System.out.println("Sauce REGION EU");
    }

    // For all capabilities please check
    // https://appium.io/docs/en/2.15/guides/caps/
    // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
    // to find the emulators/real devices names, OS versions and appium versions you can use for
    // your testings

    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("appium:automationName", "UiAutomator2");
    if (rdc.equals("true")) {
      // Allocate any available samsung device with Android version 12
      capabilities.setCapability("appium:deviceName", "Samsung.*");
      sauceOptions.setCapability("resigningEnabled", true);
      sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
      sauceOptions.setCapability("appiumVersion", "latest");
    } else {
      capabilities.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
      capabilities.setCapability("appium:appWaitActivity", ".view.activities.MainActivity");
      sauceOptions.setCapability("appiumVersion", "latest");
    }
    capabilities.setCapability("appium:platformVersion", "12");
    String appName = "mda-2.2.0-25.apk";
    capabilities.setCapability("appium:app", "storage:filename=" + appName);

    // Sauce capabilities
    sauceOptions.setCapability("name", name.getMethodName());

    sauceOptions.setCapability("build", "myApp-job-1");
    List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo");
    sauceOptions.setCapability("tags", tags);
    sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

    capabilities.setCapability("sauce:options", sauceOptions);

    driver = new AndroidDriver(url, capabilities);

    System.out.println("Job ID is: " + driver.getCapabilities().getCapability("appium:jobUuid"));
    // Setting the driver so that we can report results
    resultReportingTestWatcher.setDriver(driver);
  }

  @Test
  public void verifyPromptSortModal() {
    // Wait for the application to start and load the initial screen (products screen)
    WebDriverWait wait = new WebDriverWait(driver, ofSeconds(10));
    wait.until(ExpectedConditions.visibilityOfElementLocated(productsScreenLocator));

    driver.findElement(sortButtonLocator).click();

    // Verify the sort modal is displayed on screen
    assertThat(isDisplayed(sortModalLocator, 5)).as("Verify sort modal is displayed").isTrue();
  }

  public Boolean isDisplayed(By locator, long timeoutInSeconds) {
    try {
      WebDriverWait wait = new WebDriverWait(driver, ofSeconds(timeoutInSeconds));
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    } catch (org.openqa.selenium.TimeoutException exception) {
      return false;
    }
    return true;
  }
}
