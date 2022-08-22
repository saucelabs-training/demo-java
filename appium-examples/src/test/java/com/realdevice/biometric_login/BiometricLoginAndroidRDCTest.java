package com.realdevice.biometric_login;

import com.realdevice.SauceTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static helpers.Constants.region;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class BiometricLoginAndroidRDCTest {

    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public String deviceName;

    @Parameterized.Parameters()
    public static Collection<Object[]> crossIosData() {
        return Arrays.asList(new Object[][]{
                {"Samsung Galaxy S(7|8|9|10|20|21).*"}
        });
    }

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";

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
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

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

        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("automationName", "UiAutomator2");
        // We're using dynamic device allocation
        // See https://docs.saucelabs.com/mobile-apps/automated-testing/appium/real-devices/#dynamic-device-allocation
        capabilities.setCapability("appium:deviceName", deviceName);
        // The name of the App in the Sauce Labs storage, for more info see
        // https://docs.saucelabs.com/mobile-apps/app-storage/
        capabilities.setCapability("appium:app", "storage:filename=Android.MyDemoAppRN.apk");
        capabilities.setCapability("appium:appWaitActivity", "com.saucelabs.mydemoapp.rn.MainActivity");
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.setCapability("appiumVersion", "1.21.0");
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
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isFalse();


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
