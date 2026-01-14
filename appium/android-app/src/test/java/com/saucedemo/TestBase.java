package com.saucedemo;

import io.appium.java_client.android.AndroidDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;

public class TestBase {
  public static final String DATA_CENTER = System.getProperty("sauce.region", "us");
  public static final String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
  public static final String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
  public static final String SAUCE_URL = DATA_CENTER.equals("us") ? SAUCE_US_URL : SAUCE_EU_URL;
  @RegisterExtension public SauceTestWatcher watcher = new SauceTestWatcher();
  AndroidDriver driver;

  @BeforeEach
  public void setup(TestInfo testInfo) throws MalformedURLException {
    Capabilities capabilities = TestConfigurations.getCapabilities(testInfo);

    this.driver = new AndroidDriver(new URL(SAUCE_URL), capabilities);
    this.driver.manage().timeouts().implicitlyWait(Duration.of(15, ChronoUnit.SECONDS));
  }

  public void scrollDown(By locator) {
    new Actions(driver)
        .setActivePointer(PointerInput.Kind.TOUCH, "finger")
        .moveToElement(driver.findElement(locator))
        .clickAndHold()
        .moveByOffset(0, -500)
        .release()
        .perform();
  }

  public class SauceTestWatcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
      if (driver != null) {
        printResults(context.getDisplayName());
        try {
          ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
          driver.quit();
        } catch (Exception e) {
          System.out.println("problem with using driver: " + e);
        }
      }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      if (driver != null) {
        printResults(context.getDisplayName());

        try {
          ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
          driver.quit();
        } catch (Exception e) {
          System.out.println("problem with using driver: " + e);
        }
      }
    }

    public void printResults(String name) {
      String sessionId;
      String sauceCloud = System.getProperty("sauce.cloud", "vdc");
      if (sauceCloud.equalsIgnoreCase("rdc")) {
        sessionId = (String) driver.getCapabilities().getCapability("appium:jobUuid");
      } else {
        sessionId = String.valueOf(driver.getSessionId());
      }

      String sauceReporter =
          String.format("SauceOnDemandSessionID=%s job-name=%s", sessionId, name);
      String sauceTestLink =
          String.format("Test Job Link: https://app.saucelabs.com/tests/%s", sessionId);
      System.out.print(sauceReporter + "\n" + sauceTestLink + "\n");
    }
  }
}
