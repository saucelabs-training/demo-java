package android.tests;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import android.pages.SwagLabsPage;

import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;

import static helpers.utils.*;


public class SwagLabsTest{

    protected AndroidDriver driver;

    @BeforeMethod
    public void setup(Method method) throws IOException {
        System.out.println("Sauce - BeforeMethod hook");

        String region = getProperty("region", "eu");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = method.getName();

        String sauceUrl;
        String appName ="Android.SauceLabs.Mobile.Sample.app.2.3.0.apk";
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "Samsung Galaxy S10");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("name", methodName);
//      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
//        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
        capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");

        capabilities.setCapability("noReset", true);
        capabilities.setCapability("sauceLabsImageInjectionEnabled", true);

        // Launch remote browser and set it as the current thread
        driver = new AndroidDriver(url, capabilities);
    }

    @Test
    public void imageInjection_scan_QR_code() throws InterruptedException {
        System.out.println("Sauce - start test imageInjection_scan_QR_code");

        // init
        SwagLabsPage page = new SwagLabsPage(driver);

        // Login
        page.login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(page.isOnProductsPage());

        // Select QR Code Scanner from the menu
        page.clickMenu();
        page.selecMenuQRCodeScanner();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/resources/images/qr-code.png");
        ((JavascriptExecutor)driver).executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isAndroidBrowserOpened(driver);
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }

}