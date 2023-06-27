package com.examples.mid_session_app_installs;

import com.google.common.collect.ImmutableMap;
import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import lombok.var;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.helpers.Constants.*;

/**
 * Android Native App Tests
 */
public class MidSessionAppInstallsAndroidTest {

    By productsScreenLocator = By.xpath("//*[@content-desc=\"products screen\"]");
    By sortButtonLocator = By.xpath("//*[@content-desc=\"sort button\"]");
    By sortModalLocator = By.xpath("//*[@content-desc=\"active option\"]");


    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Sauce Android Native App  - Before hook");
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                System.out.println("Sauce REGION US");
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                System.out.println("Sauce REGION EU");
                break;
        }

        // For all capabilities please check
        // http://appium.io/docs/en/writing-running-appium/caps/#general-capabilities
        // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
        // to find the emulators/real devices names, OS versions and appium versions you can use for your testings

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:deviceName", "Samsung.*");
        sauceOptions.setCapability("resigningEnabled", true);
        sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
        capabilities.setCapability("appium:platformVersion", "13");
        String appName = "mda-1.0.14-17.apk";
        capabilities.setCapability("appium:app", "storage:filename=" +appName);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo", "mid-session-app-installs");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            driver = new AndroidDriver(url, capabilities);
        } catch (Exception e){
            System.out.println("Error to create Android Driver: " + e.getMessage());
            return;
        }
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void upgradeAppInMidSession() throws MalformedURLException {
        // Navigate to the "About" page
        navigateToAbout();

        // Install a new version
        var results = driver.executeScript("mobile:installApp", ImmutableMap.of("appPath", "storage:filename=mda-1.0.17-20.apk"));
        System.out.println(results);

        // Launch the new app
        driver.executeScript("mobile: startActivity", ImmutableMap.of("intent", "com.saucelabs.mydemoapp.android/.view.activities.SplashActivity"));

        waiting(3);

        // Navigate to the "About" page
        navigateToAbout();
    }

    private void navigateToAbout(){
        driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/menuIV")).click();
        List<WebElement> menuItems = driver.findElements(By.id("com.saucelabs.mydemoapp.android:id/itemTV"));
        // About is the 6 item
        menuItems.get(5).click();
        // For the Video...
        waiting(5);
    }
    private void waiting(int sec){
        try
        {
            Thread.sleep(sec*1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }





}
