package com.realdevice;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
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

public class AndroidNativeAppTest {
    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();
    public AppiumDriver<MobileElement> driver;
    private MutableCapabilities capabilities;

    public AppiumDriver<MobileElement> getDriver() {
        return driver;
    }

    @Before
    public void setup(){
        //Arrange
        capabilities = new MutableCapabilities();
        /*
         * Pick your device
         * */
        //Not specifying platformVersion or the exact device is the most likely to
        //find a device in the cloud
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("deviceName", "Google Pixel.*");
        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", "90");
        capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");
        capabilities.setCapability("name", name.getMethodName());
    }

    @Test
    public void shouldOpenApp() throws MalformedURLException {
        capabilities.setCapability("app",
                "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

        driver = new AndroidDriver<>(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);

        //Act
        WebDriverWait wait = new WebDriverWait(getDriver(), 10000);
        By usernameLocator = MobileBy.AccessibilityId("test-Username");
        //Assert
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator));
    }

    @Test
    public void setAppNameDynamically() throws MalformedURLException {
        /*
        * We can dynamically set an app name from environment variables
        * Just set the value in command line before running `mvn` command
        * */
        capabilities.setCapability("app", System.getenv("ANDROID_APP"));

        driver = new AndroidDriver<>(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);

        //Act
        WebDriverWait wait = new WebDriverWait(getDriver(), 10000);
        By usernameLocator = MobileBy.AccessibilityId("test-Username");
        //Assert
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLocator));
    }
}
