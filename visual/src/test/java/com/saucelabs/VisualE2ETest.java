package com.saucelabs;

import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class VisualE2ETest {

  protected WebDriver webDriver;

  public String sauceUsername = System.getenv("SAUCE_USERNAME");
  public String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
  public String screenerApiKey = System.getenv("SCREENER_API_KEY");
  public String seleniumProtocol = System.getenv("SELENIUM_PROTOCOL");
  public String seleniumHost = System.getenv("SELENIUM_HOST");
  public String seleniumPort = System.getenv("SELENIUM_PORT");
  public DesiredCapabilities capabilities;


  public String browserName;

  @BeforeTest
  public void setUp() throws Exception{
    if (sauceUsername == null || sauceUsername.isEmpty()) {
      throw new Exception("SAUCE_USERNAME environment variable needs to be defined");
    }

    if (sauceAccessKey == null || sauceAccessKey.isEmpty()) {
      throw new Exception("SAUCE_ACCESS_KEY environment variable needs to be defined");
    }

    if (screenerApiKey == null || screenerApiKey.isEmpty()) {
      throw new Exception("SCREENER_API_KEY environment variable needs to be defined");
    }

    if (seleniumProtocol == null || seleniumProtocol.isEmpty()) {
        seleniumProtocol = "https";
    }

    if (seleniumHost == null || seleniumHost.isEmpty()) {
        seleniumHost = "hub.screener.io/wd/hub";
    }

    if (seleniumPort == null || seleniumPort.isEmpty()) {
        seleniumPort = "443";
    }

    capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
    capabilities.setCapability(CapabilityType.BROWSER_VERSION, "80.0");
    capabilities.setCapability(CapabilityType.PLATFORM_NAME, "Windows 10");

    MutableCapabilities sauceOptions = new MutableCapabilities();
    sauceOptions.setCapability("username", sauceUsername);
    sauceOptions.setCapability("accesskey", sauceAccessKey);

    capabilities.setCapability("sauce:options", sauceOptions);
  }

  @Test
  public void testE2E() {
      MutableCapabilities visualOptions = new MutableCapabilities();
      visualOptions.setCapability("apiKey", screenerApiKey);
      visualOptions.setCapability("projectName", "visual-e2e-test");
      visualOptions.setCapability("viewportSize", "1280x1024");
      capabilities.setCapability("sauce:visual", visualOptions);

      try {
        webDriver = new RemoteWebDriver(new URL(String.format("%s://%s:%s/wd/hub", seleniumProtocol, seleniumHost, seleniumPort)), capabilities);
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        Assert.assertNull(e, "The url for the selenium host is not valid");
      }

      webDriver.get("https://screener.io");
      JavascriptExecutor js = (JavascriptExecutor) webDriver;
      js.executeScript("/*@visual.init*/", "My Visual Test");
      js.executeScript("/*@visual.snapshot*/", "Home");

      Map<String,Object> response = (Map<String,Object>)js.executeScript("/*@visual.end*/");
      Assert.assertTrue((Boolean)response.get("passed"), (String)response.get("message"));
  }
}
