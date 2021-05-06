package com.realdevice.unifiedplatform.image_injection;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.net.URL;

import static helpers.Constants.region;

public class ImageInjectionAndroidTest {

    String usernameID = "test-Username";
    String passwordID = "test-Password";
    String submitButtonID = "test-LOGIN";

    By ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");

    By testMenu = By.xpath("//android.view.ViewGroup[@content-desc='test-Menu']");
    By testMenuItemQRCode = By.xpath("//android.view.ViewGroup[@content-desc='test-QR CODE SCANNER']");
    By testMenuItemWebView = By.xpath("//android.view.ViewGroup[@content-desc='test-WEBVIEW']");

    protected AndroidDriver driver;

    @RegisterExtension
    public MyTestWatcher myTestWatcher = new MyTestWatcher();

    @BeforeEach
    public void setup(TestInfo testInfo) throws IOException {

        System.out.println("Sauce - BeforeEach hook");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = testInfo.getDisplayName();

        String sauceUrl;
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

        capabilities.setCapability("sauceLabsImageInjectionEnabled", true);
        capabilities.setCapability("autoGrantPermissions", true);

        // Launch remote browser and set it as the current thread
        driver = new AndroidDriver(url, capabilities);
    }

    @Test
    @DisplayName("imageInjectionScanQRcode")
    @Tag("imageInjection")
    @Tag("Android")
    public void imageInjectionScanQRcode() throws InterruptedException {
        System.out.println("Sauce - start test imageInjection_scan_QR_code");

        // Login
        login("standard_user", "secret_sauce");

        // Verificsation
        Assertions.assertTrue(isOnProductsPage());

        // Select QR Code Scanner from the menu
        clickMenu();
        selecMenuQRCodeScanner();

        // inject the image - provide the transformed image to the device with this command
        Utils utils = new Utils();
        String qrCodeImage = utils.encoder("src/test/java/com/native_app/image_injection/images/qr-code.png");
        ((JavascriptExecutor)driver).executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        utils.isAndroidBrowserOpened(driver);

        // This is not need only for the video
        try
        {
            Thread.sleep(5000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }

    }

    public class MyTestWatcher implements TestWatcher {
        @Override
        public void testSuccessful(ExtensionContext context) {
            try {
                System.out.println("Test Passed!");
                ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
            } catch (Exception ignored) {
            } finally {
                    driver.quit();
            }
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            try {
                System.out.println("Test Failed!");
                ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
            } catch (Exception ignored) {
            } finally {
                driver.quit();
            }
        }
    }

    private void login(String user, String pass) {
        try {
            driver.context("NATIVE_APP");

            WebDriverWait wait = new WebDriverWait(driver, 10);
            final WebElement usernameEdit = wait.until(ExpectedConditions.visibilityOfElementLocated(new MobileBy.ByAccessibilityId(usernameID)));

            usernameEdit.click();
            usernameEdit.sendKeys(user);


            WebElement passwordEdit = (WebElement) driver.findElementByAccessibilityId(passwordID);
            passwordEdit.click();
            passwordEdit.sendKeys(pass);

            WebElement submitButton = (WebElement) driver.findElementByAccessibilityId(submitButtonID);
            submitButton.click();

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    private boolean isOnProductsPage() {

        //Create an instance of a Selenium explicit wait so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //wait for the product field to be visible and store that element into a variable
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(ProductTitle));
        } catch (TimeoutException e){
            return false;
        }
        return true;
    }

    private void clickMenu() {
        driver.findElement(testMenu).click();
    }
    private void selecMenuQRCodeScanner() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement QCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(testMenuItemQRCode));
        QCCodeMenu.click();
    }
}