package com.examples.biometric_login;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static com.helpers.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class BiometricLoginAndroidRDCTest {

    By productsScreenLocator = By.xpath("//*[@content-desc=\"products screen\"]");
    By menuLocator = By.xpath("//*[@content-desc=\"open menu\"]");
    By menuBiometricLocator = By.xpath("//*[@content-desc=\"menu item biometrics\"]");
    By biometricScreenLocator = By.xpath("//*[@content-desc=\"biometrics screen\"]");
    By biomerticSwitch = By.xpath("//*[@content-desc=\"biometrics switch\"]");
    By menuLoginLocator = By.xpath("//*[@content-desc=\"menu item log in\"]");
    By biometricModal = By.xpath("//android.widget.TextView[contains(@text,\"Sign in with FingerPrint\") or contains(@text,\"Sign in with FaceID\")]");

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
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

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:deviceName", "Samsung Galaxy S(7|8|9|10|20|21).*");
        capabilities.setCapability("appium:platformVersion", "12");
        capabilities.setCapability("appium:newCommandTimeout", 240);
        String appName = "Android.MyDemoAppRN.apk";
        capabilities.setCapability("appium:app", "storage:filename=" +appName);
        capabilities.setCapability("appium:appWaitActivity","com.saucelabs.mydemoapp.rn.MainActivity");
        capabilities.setCapability("autoGrantPermissions", true);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "biometricAuth-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android","Biometric Login");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        // Select only phone devices
        sauceOptions.setCapability("phoneOnly", true);
        // Enable touchID
        sauceOptions.setCapability("resigningEnabled", true);
        // NOTE: this is needed to tell Sauce Labs that the biometrics need to be mocked
        sauceOptions.setCapability("allowTouchIdEnroll", true);

        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new AndroidDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);

        // *** before each test - Prepare the biometrics by enabling it in the menu ***

        // (1) Verify we are in the catalog page
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
        // (2) Open the menu
        driver.findElement(menuLocator).click();

        // (3) Open Biometric page
        driver.findElement(menuBiometricLocator).click();
        // (4) Wait for the Biometric page to be shown
        waitDisplayed(biometricScreenLocator,10);
        // (5) enable Biometric
        WebElement biometricSwitchEle = driver.findElement(biomerticSwitch);
        String switchValue = biometricSwitchEle.getText();

        if (switchValue.equals("OFF"))
            biometricSwitchEle.click();

        // Go to the login
        driver.findElement(menuLocator).click();
        driver.findElement(menuLoginLocator).click();

    }

    @Test
    public void biometricLoginWithMatchingTouch () throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with matching touch");

        /**
         * The biometrics test starts here
         */
        // Biometrics login will automatically be triggered, so wait for the modal.
        if (isDisplayed(biometricModal, 5)){
            driver.executeScript("sauce:biometrics-authenticate=true");
        }
        // Verify we are in the catalog page
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();

    }

    @Test
    public void biometricLoginWithNonMatchingTouch () throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with a non matching touch");

        /**
         * The biometrics test starts here
         */
        // Biometrics login will automatically be triggered, so wait for the modal.
        if (isDisplayed(biometricModal, 5)){
            driver.executeScript("sauce:biometrics-authenticate=false");
        }

        // Verify we are NOT in the catalog page
        assertThat(isDisplayed(productsScreenLocator, 5)).as("Verify catalog page").isFalse();


    }


    public Boolean isDisplayed(By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

    public WebElement waitDisplayed(By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            System.out.println("*** The element wasn't diplayed ***");
            return null;
        }

    }

    public Boolean isElementDisplayed(WebElement elem, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOf(elem));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }

}
