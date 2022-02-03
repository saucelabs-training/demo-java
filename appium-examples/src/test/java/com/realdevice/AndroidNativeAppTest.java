package com.realdevice;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import static helpers.Constants.region;

/**
 * Android Native App Tests
 */
public class AndroidNativeAppTest {

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com/wd/hub";

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    private AndroidDriver driver;

    public AndroidDriver getDriver() {
        return driver;
    }

    @Before
    public void setup() throws MalformedURLException {

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

        //find a device in the cloud
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "UiAutomator2");
        //Allocate any avilable samsung device with Android version 12
        capabilities.setCapability("appium:deviceName", "Samsung.*");
        capabilities.setCapability("appium:platformVersion", "12");
        capabilities.setCapability("appium:appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
        //        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("appium:app",
                "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new AndroidDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);

    }

    @Test
    public void shouldOpenApp() throws MalformedURLException {

        //Act
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        By usernameLocator = MobileBy.AccessibilityId("test-Username");
        //Assert
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator));

    }
}
