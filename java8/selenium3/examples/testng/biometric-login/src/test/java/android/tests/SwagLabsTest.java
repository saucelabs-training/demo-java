package android.tests;

import android.pages.SwagLabsPage;
import helpers.AndroidSettings;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static helpers.utils.getProperty;


public class SwagLabsTest{

    protected AndroidDriver driver;
    String sessionId;

    @BeforeMethod
    public void setup(Method method) throws IOException {
        System.out.println("Sauce - BeforeMethod hook");

        String region = getProperty("region", "eu");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = method.getName();

        String sauceUrl;
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);

        String appName ="sample-app-android.apk";
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("app", "sauce-storage:" + appName);
        capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");


        // Launch remote browser and set it as the current thread
        driver = new AndroidDriver(url, capabilities);
        sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
    }

    @Test
    public void Biometric_login_with_matching_touch() throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with matching touch");

        // init
        SwagLabsPage page = new SwagLabsPage(driver);

        // If the biometry is not shown on iOS, enable it on the phone
        if (page.isBiometryDisplayed() == false){
            AndroidSettings androidSettings = new AndroidSettings(driver);
            androidSettings.enableBiometricLogin();
        }

        // Login with biometric auth
        page.login(true);

        // Verificsation
        Assert.assertTrue(page.isOnProductsPage());

    }

    @Test
    public void Biometric_login_with_non_matching_touch() throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with a non matching touch");

        // init
        SwagLabsPage page = new SwagLabsPage(driver);

        // If the biometry is not shown on iOS, enable it on the phone
        if (page.isBiometryDisplayed() == false){
            AndroidSettings androidSettings = new AndroidSettings(driver);
            androidSettings.enableBiometricLogin();
        }

        // Login with biometric auth
        page.login(false);

        // Verificsation
        Assert.assertTrue(page.isRetryBiometryDisplay(),"Retry is not shown");

    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }

}