package android.tests;

import helpers.AndroidSettings;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

import static helpers.HelperUtil.getProperty;


public class SwagLabsTest{

    protected AndroidDriver driver;
    protected String sessionId;

    private String biometryID = "test-biometry";

    private By ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");

    private final int DEFAULT_PIN = 1234;
    private final int INCORRECT_PIN = 4321;

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
        //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
//        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

        capabilities.setCapability("appWaitActivity", "com.swaglabsmobileapp.MainActivity");


        // Launch remote browser and set it as the current thread
        driver = new AndroidDriver(url, capabilities);
        sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
    }

    @Test
    public void biometricLoginWithMatchingTouch() throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with matching touch");

        // If the biometry is not shown on iOS, enable it on the phone
        if (!this.isBiometryDisplayed()){
            AndroidSettings androidSettings = new AndroidSettings(driver);
            androidSettings.enableBiometricLogin();
        }

        // Login with biometric auth
        this.login(true);

        // Verificsation
        Assert.assertTrue(this.isOnProductsPage());

    }

    @Test
    public void biometricLoginWithNonMatchingTouch() throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with a non matching touch");

        // If the biometry is not shown on iOS, enable it on the phone
        if (!this.isBiometryDisplayed()){
            AndroidSettings androidSettings = new AndroidSettings(driver);
            androidSettings.enableBiometricLogin();
        }

        // Login with biometric auth
        this.login(false);

        // Verificsation
        Assert.assertTrue(this.isRetryBiometryDisplay(),"Retry is not shown");

    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("Sauce - AfterMethod hook");
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }

    public void login(boolean successful) {
        try {

            WebElement biometryButton = (WebElement) driver.findElementByAccessibilityId(biometryID);
            biometryButton.click();

            if (successful){
                driver.fingerPrint(DEFAULT_PIN);
            } else {
                driver.fingerPrint(INCORRECT_PIN);
            }


        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    public boolean isOnProductsPage() {

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

    public boolean isRetryBiometryDisplay(){

        try {
            WebElement fingerprintLink = driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Not recognized\")"));
            return fingerprintLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBiometryDisplayed() {
        try {

            WebElement biometryButton = (WebElement) driver.findElementByAccessibilityId(biometryID);
            return biometryButton.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

}