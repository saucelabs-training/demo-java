package com.examples.deep_link;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
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
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.helpers.Constants.*;

/**
 * Android Native App Tests
 */
public class DeepLinkAndroidTest {

    By productsScreenLocator = By.xpath("//*[@content-desc=\"products screen\"]");
    String usernameEditAccessibility = "Username input field";

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Sauce Android Deep Link - Before hook");

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
        if (rdc.equals("true")) {
            System.out.println("Run on real device");
            //Allocate any avilable samsung device with Android version 12
            capabilities.setCapability("appium:deviceName", "Samsung.*");
        }
        else {
            System.out.println("Run on emulator");
            capabilities.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
        }
        capabilities.setCapability("appium:platformVersion", "12");
        String appName = "Android.MyDemoAppRN.apk";
        capabilities.setCapability("appium:app", "storage:filename=" +appName);
        capabilities.setCapability("appium:appWaitActivity","com.saucelabs.mydemoapp.rn.MainActivity");

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        if (rdc.equals("true")) {
            sauceOptions.setCapability("resigningEnabled", true);
            sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
        }

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
    public void openLoginPageWithDeepLink() throws MalformedURLException {
        //Wait for the application to start and load the initial screen (products screen)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsScreenLocator));

        // use deep link to open directly the login screen
        HashMap<String, Object> params = new HashMap<>();
        params.put("url",  "mydemoapprn://login");
        params.put("package", "com.saucelabs.mydemoapp.rn");
        driver.executeScript("mobile:deepLink", params);

        // Verify we are in the Login page
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(usernameEditAccessibility)));

        // For video purpose
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }





}
