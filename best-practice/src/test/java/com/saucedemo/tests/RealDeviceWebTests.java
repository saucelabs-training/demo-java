package com.saucedemo.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.saucedemo.Endpoints;
import com.saucedemo.MobileTestsBase;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;

/** Real Device Web Tests. */
@RunWith(Parameterized.class)
public class RealDeviceWebTests extends MobileTestsBase {
  @Parameterized.Parameter public String deviceName;

  @Parameterized.Parameters()
  public static Collection<Object[]> iosConfigurations() {
    return Arrays.asList(
        new Object[][] {
          {"iPhone 11.*"}, {"iPhone 12.*"}, {"iPad 10.*"}, {"iPad Air.*"}, {"iPad.*"},
          // Duplication below for demo purposes of massive parallelization
          // https://saucelabs.com/products/platform-configurator#/
        });
  }

  @Before
  public void setUp() throws MalformedURLException {
    /*
    * if you set the browserName => always starts with webcontext
        if you set the app => always starts with native context
        if you set the package/bundleId => always starts with native context
        if you have a hybrid app and set autoWebview  => always starts with webview
    * */
    MutableCapabilities capabilities = new MutableCapabilities();
    capabilities.setCapability("platformName", "iOS");
    capabilities.setCapability("browserName", "Safari");
    capabilities.setCapability("appium:language", "en");
    capabilities.setCapability("appium:deviceName", deviceName);

    MutableCapabilities sauceOptions = new MutableCapabilities();
    sauceOptions.setCapability("name", testName.getMethodName());
    sauceOptions.setCapability("build", buildName);

    capabilities.setCapability("sauce:options", sauceOptions);

    driver = new IOSDriver(Endpoints.getRealDevicesHub(), capabilities);
  }

  @Test
  public void loginWorks() {
    LoginPage loginPage = new LoginPage(getDriver());
    loginPage.visit();
    loginPage.login("standard_user");
    assertTrue(new ProductsPage(driver).isDisplayed());
  }

  @Test(expected = TimeoutException.class)
  public void lockedOutUser() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.visit();
    loginPage.login("locked_out_user");
    assertFalse(new ProductsPage(driver).isDisplayed());
  }

  @Test(expected = TimeoutException.class)
  public void invalidCredentials() {
    LoginPage loginPage = new LoginPage(driver);
    loginPage.visit();
    loginPage.login("foo_bar_user");
    assertFalse(new ProductsPage(driver).isDisplayed());
  }
}
