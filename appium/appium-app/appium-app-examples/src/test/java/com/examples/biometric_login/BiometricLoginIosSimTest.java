package com.examples.biometric_login;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
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
import java.util.List;

import static com.helpers.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class BiometricLoginIosSimTest {

    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public String deviceName;

    @Parameterized.Parameter(1)
    public String platformVersion;

    @Parameterized.Parameters()
    public static Collection<Object[]> crossIosData() {
        return Arrays.asList(new Object[][]{
                {"iPhone 8 Plus Simulator", "15.2"}, // This one has TouchID
                {"iPhone 12 Simulator", "15.2"} // This one has FaceID
        });
    }

    By productsScreenLocator = By.id("products screen");
    By menuLocator = By.id("tab bar option menu");
    By menuBiometricLocator = By.id("menu item biometrics");
    By biometricScreenLocator = By.id("biometrics screen");
    By biomerticSwitch = By.id("biometrics switch");
    By menuLoginLocator = By.id("menu item log in");

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private IOSDriver driver;


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

        // For all capabilities please check
        // http://appium.io/docs/en/writing-running-appium/caps/#general-capabilities
        // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
        // to find the simulators names, OS versions and appium versions you can use for your testings
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCuiTest");
        capabilities.setCapability("appium:deviceName", deviceName);
        capabilities.setCapability("appium:platformVersion", platformVersion);
        capabilities.setCapability("appium:newCommandTimeout", 240);
        String appName = "iOS.MyDemoAppRN.zip";
        capabilities.setCapability("app", "storage:filename=" +appName);
//        capabilities.setCapability("autoAcceptAlerts", true);

        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "biometricAuth-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "iOS","Biometric Login");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new IOSDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);

    }

    @Test
    public void biometricLoginWithMatchingTouch() throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with matching touch");

        // Prepare the biometrics by enabling it in the menu ***
        prepareBiometrics();

        // Go to the login
        driver.findElement(menuLocator).click();
        driver.findElement(menuLoginLocator).click();

        // For iOS we can have permission modal
        try {
            String selector = "**/XCUIElementTypeStaticText[`label CONTAINS \"Do you want to allow\"`]";
            // Only wait 3 seconds for it, then it will not slow down if it's not there
            WebElement allowBiometricsModal = (WebElement) driver.findElement(AppiumBy.iOSClassChain(selector));
            if (isElementDisplayed(allowBiometricsModal,3)) {
                driver.findElement(AppiumBy.accessibilityId("OK")).click();
            }
        } catch (Exception e)  {
            // Do nothing
        }

        /**
         * The biometrics test starts here
         */

        // Biometrics login will automatically be triggered, so wait for the modal.
        String selector = "**/XCUIElementTypeOther/**/XCUIElementTypeStaticText[`label CONTAINS \"Face ID\" or label CONTAINS \"Touch ID\"`]";
        WebElement iosBiometricsModalSelector = (WebElement) driver.findElement(AppiumBy.iOSClassChain(selector));
        if (isElementDisplayed(iosBiometricsModalSelector,1)) {
        // For iOS: https://appium.io/docs/en/commands/device/simulator/touch-id/
            driver.performTouchID(true);
        }

        // Verify we are in the catalog page
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();

    }

    @Test
    public void biometricLoginWithNonMatchingTouch() throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with a non matching touch");

        // Prepare the biometrics by enabling it in the menu ***
        prepareBiometrics();

        // Go to the login
        driver.findElement(menuLocator).click();
        driver.findElement(menuLoginLocator).click();

        // For iOS we can have permission modal
        try {
            String selector = "**/XCUIElementTypeStaticText[`label CONTAINS \"Do you want to allow\"`]";
            // Only wait 3 seconds for it, then it will not slow down if it's not there
            WebElement allowBiometricsModal = (WebElement) driver.findElement(AppiumBy.iOSClassChain(selector));
            if (isElementDisplayed(allowBiometricsModal,3)) {
                driver.findElement(AppiumBy.accessibilityId("OK")).click();
            }
        } catch (Exception e)  {
            // Do nothing
        }

        /**
         * The biometrics test starts here
         */

        // Biometrics login will automatically be triggered, so wait for the modal.
        String selector = "**/XCUIElementTypeOther/**/XCUIElementTypeStaticText[`label CONTAINS \"Face ID\" or label CONTAINS \"Touch ID\"`]";
        WebElement iosBiometricsModalSelector = (WebElement) driver.findElement(AppiumBy.iOSClassChain(selector));
        if (isElementDisplayed(iosBiometricsModalSelector,1)) {
            // For iOS: https://appium.io/docs/en/commands/device/simulator/touch-id/
            driver.performTouchID(false);
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

    public void prepareBiometrics(){

        boolean biometricsDisabled = true;

        driver.terminateApp("com.saucelabs.mydemoapp.rn");
        driver.activateApp("com.saucelabs.mydemoapp.rn");

        // (1) Verify we are in the catalog page
        assertThat(isDisplayed(productsScreenLocator, 10)).as("Verify catalog page").isTrue();
        // (2) Open the menu
        driver.findElement(menuLocator).click();

        // (3) Open Biometric page
        driver.findElement(menuBiometricLocator).click();

        // It could be that biometrics is not enabled and an alert will be shown.
        // The screen will then not be in the foreground
        WebElement biometricPage = waitDisplayed(biometricScreenLocator,3);
        if (biometricPage != null) // The biometrix screen is there so no alert
            biometricsDisabled = false;


        // Biometrics is disabled, so enable it
        if (biometricsDisabled) {
            // iOS can easily be enabled, see also this Appium Command
            // https://appium.io/docs/en/commands/device/simulator/toggle-touch-id-enrollment/
            // When it has been enabled, restart the app and see if we can now enable biometrics in the app
            driver.toggleTouchIDEnrollment(true);
            // Call again this function after we enable the biometric in the device
            prepareBiometrics();
        }

        // Now enable biometrics in the app
        WebElement biometricSwitchEle = driver.findElement(biomerticSwitch);
        String switchValue = biometricSwitchEle.getText();

        if (switchValue.equals("0"))
            biometricSwitchEle.click();

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
