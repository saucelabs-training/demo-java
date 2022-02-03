package com.realdevice;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

import static helpers.Constants.region;
import static org.junit.Assert.assertTrue;

public class IOSNativeAppTest {

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com/wd/hub";

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    public IOSDriver driver;
    public IOSDriver getDriver() {
        return driver;
    }

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
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

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCuiTest");
        //Allocate any avilable iPhone device with version 14
        capabilities.setCapability("appium:deviceName", "iPhone.*");
        capabilities.setCapability("appium:platformVersion", "14");
        //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
        //      capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("appium:app",
                "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");

        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
//        sauceOptions.setCapability("noReset", "true");
        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new IOSDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void shouldOpenApp() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10000);
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId("test-Username")));
        assertTrue(loginField.isDisplayed());
    }
}
