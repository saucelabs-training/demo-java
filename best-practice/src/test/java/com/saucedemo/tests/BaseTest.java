package com.saucedemo.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

import static com.saucedemo.Constants.*;

public class BaseTest {
    protected RemoteWebDriver driver;

    protected String WEB_URL = "https://www.saucedemo.com/";

    @Parameterized.Parameter
    public String platform;
    @Parameterized.Parameter(1)
    public String browserDeviceName;
    @Parameterized.Parameter(2)
    public String browserPlatformVersion;
    @Parameterized.Parameter(3)
    public String platformName;

    @Parameterized.Parameters()
    public static Collection<Object[]> crossPlatformData() {
        Collection<Object[]> browserList;
        browserList = Arrays.asList(new Object[][] {
                { "desktop", "firefox", "latest", "Windows 11" },
                { "desktop", "chrome", "latest", "Windows 10" }
        });

        boolean includeMac = true;
        if (INCLUDE_MAC != null) {
          includeMac = Boolean.parseBoolean(INCLUDE_MAC);
        }
        if (includeMac) {
          Collection<Object[]> macBrowserList;
          macBrowserList = Arrays.asList(new Object[][] {
                  { "desktop", "safari", "latest", "macOS 11.00" },
                  { "desktop", "chrome", "latest-1", "macOS 13" }
          });
          browserList.addAll(macBrowserList);
        }

        return browserList;
    }
    @Rule
    public TestName name = new TestName();

    @Before
    public void setup() throws MalformedURLException {

        System.out.println("BeforeMethod hook");
        URL url;

        if (SAUCE_URL_OVERRIDE != null) {
          url = new URL(SAUCE_URL_OVERRIDE);
        } else {
          switch (region) {
              case "us":
                  url = new URL(SAUCE_US_URL);
                  break;
              case "eu":
              default:
                  url = new URL(SAUCE_EU_URL);
                  break;
          }
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

        if (!isBuildCap) { //handle build cap
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH");
            String buildLocal = "sauceDemo-" +dateTime.format(formatter);
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


    @Rule
    public TestRule watcher = new TestWatcher() {

        @Override
        protected void succeeded(Description description) {
            if(driver != null)
            {
                System.out.println("Test Passed!");
                driver.executeScript("sauce:job-result=passed");
                driver.quit();
            }
        }

        @Override
        public void failed(Throwable e, Description description) {
            if(driver != null)
            {
                System.out.println("Test Failed!");
                driver.executeScript("sauce:job-result=failed");
                driver.executeScript("sauce:context=" +e.getMessage());
                driver.quit();
            }
        }

    };

}
