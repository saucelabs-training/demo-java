package ios.tests;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ios.pages.SwagLabsPage;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static helpers.utils.*;

public class SwagLabsTest {

    protected IOSDriver driver;
    String sessionId;

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        System.out.println("Sauce - BeforeMethod hook");

        String region = getProperty("region", "eu");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = method.getName();

        String sauceUrl;
        String appID ="";
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
            appID ="e7fdaa5f-36d6-40c7-9a51-0bb71acf44c3";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
            appID = "198a3d97-6d78-4ad9-895e-1f3f94974a2c";
        }

        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);


        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "iPhone 8*");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITEST");
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("app", "storage:" +appID);
        capabilities.setCapability("noReset", true);
        capabilities.setCapability("sauceLabsImageInjectionEnabled", true);
        capabilities.setCapability("autoAcceptAlerts", true);

        // Launch remote browser and set it as the current thread
        driver = new IOSDriver(url, capabilities);
        sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
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

        // Accept access if asked
        page.acceptCameraAccess();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/resources/images/qr-code.png");
        ((JavascriptExecutor)driver).executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isIosApplicationRunning(driver, "com.apple.mobilesafari");
    }


    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }

}