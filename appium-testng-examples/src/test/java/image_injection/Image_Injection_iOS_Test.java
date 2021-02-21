package image_injection;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static helpers.Utils.*;
import static helpers.Config.region;

public class Image_Injection_iOS_Test {

    By usernameID = By.id("test-Username");
    By passwordID = By.id("test-Password");
    By submitButton = By.id("test-LOGIN");
    String ProductTitleSelector = "type==\"XCUIElementTypeStaticText\" && name==\"PRODUCTS\"";
    By testMenu = By.name("test-Menu");
    By testMenuItemWebView = By.name("test-WEBVIEW");
    By testMenuItemQRCode = By.name("test-QR CODE SCANNER");
    By acceptCameraButton = By.name("OK");

    protected IOSDriver driver;

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        System.out.println("Sauce - BeforeMethod hook ");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = method.getName();

        String sauceUrl;
        String appName ="iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.3.0.ipa";
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }

        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);


        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("deviceName", "iPhone 8*");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITEST");
        capabilities.setCapability("name", methodName);
        //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
//        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");

        capabilities.setCapability("noReset", true);
        capabilities.setCapability("sauceLabsImageInjectionEnabled", true);
        capabilities.setCapability("autoAcceptAlerts", true);

        // Launch remote browser and set it as the current thread
        driver = new IOSDriver(url, capabilities);
    }


    @Test
    public void imageInjection_scan_QR_code() throws InterruptedException {
        System.out.println("Sauce - start test imageInjection_scan_QR_code");

        // Login
        login("standard_user", "secret_sauce");

        // Verificsation
        Assert.assertTrue(isOnProductsPage());

        // Select QR Code Scanner from the menu
        clickMenu();
        selecMenuQRCodeScanner();

        // Accept access if asked
        acceptCameraAccess();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/java/image_injection/images/qr-code.png");
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

    private void login(String user, String pass) {
        try {

            WebElement usernameEdit = driver.findElement(usernameID);
            usernameEdit.click();
            usernameEdit.sendKeys(user);

            WebElement passwordEdit = driver.findElement(passwordID);
            passwordEdit.click();
            passwordEdit.sendKeys(pass);

            driver.findElement(submitButton).click();

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    private boolean isOnProductsPage() {
        return driver.findElementByIosNsPredicate(ProductTitleSelector).isDisplayed();
    }

    private void clickMenu() {
        driver.findElement(testMenu).click();
    }

    private void selecMenuQRCodeScanner() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement QCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(testMenuItemQRCode));
        QCCodeMenu.click();
    }

    public void acceptCameraAccess(){
        // In Sauce Labs (Legacy) RDC Android permissions are enabled by default.
        /// iOS needs to be done with automation
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            WebElement acceptCameraBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(acceptCameraButton));
            acceptCameraBtn.click();
        } catch (Exception e) {
            // Do nothing, the alert was not shown
        }
    }


}