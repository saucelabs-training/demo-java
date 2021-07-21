package com.emusim.biometric_login;

import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static helpers.Constants.rdc;
import static helpers.Constants.region;
import static org.assertj.core.api.Assertions.assertThat;

public class BiometricLoginIosTest {

    @Rule
    public TestName name = new TestName();

    protected IOSDriver driver;

    private By biometryButton = By.id("test-biometry");
    private String ProductTitleSelector = "type==\"XCUIElementTypeStaticText\" && name==\"PRODUCTS\"";

    @Before
    public void setup() throws IOException {

        System.out.println("Sauce - BeforeMethod hook");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceUrl;

        MutableCapabilities capabilities = new MutableCapabilities();
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }

        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl + "/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);

        if (Boolean.valueOf(rdc)) {
            System.out.println("Sauce - Run on real device");
            capabilities.setCapability("deviceName", "iPhone 8*");
            // Enable touchID
            capabilities.setCapability("allowTouchIdEnroll", true);

            //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
            //capabilities.setCapability("app", "storage:filename=" +appName);
            capabilities.setCapability("app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa");
        } else { // Simulator
            System.out.println("Sauce - Run on virtual mobile device");
            capabilities.setCapability("deviceName", "iPhone 8 Simulator");
            capabilities.setCapability("platformVersion", "13.4");
            //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
            //  capabilities.setCapability("app", "sauce-storage:" + appName);
            capabilities.setCapability("app", "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.zip");
        }
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITEST");
        capabilities.setCapability("name", name.getMethodName());

        // Launch remote browser and set it as the current thread
        driver = new IOSDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void biometricLoginWithMatchingTouch () throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with matching touch");

        // If the biometry is not shown on iOS, enable it on the phone
        if (!this.isBiometryDisplayed()){
            System.out.println("Sauce - Need to enable the biometry and restart the device");
            driver.toggleTouchIDEnrollment(true);
            driver.resetApp();
        }

        // Login with biometric auth
        this.login(true);

        // Verificsation
        assertThat(isOnProductsPage()).isTrue();
    }

    @Test
    public void biometricLoginWithNonMatchingTouch () throws InterruptedException {
        System.out.println("Sauce - start test Biometric login with a non matching touch");

        // If the biometry is not shown on iOS, enable it on the phone
        if (!this.isBiometryDisplayed()){
            System.out.println("Sauce - Need to enable the biometry and restart the device");
            driver.toggleTouchIDEnrollment(true);
            driver.resetApp();

        }

        // Login with biometric auth
        this.login(false);

        // Verificsation
        assertThat(this.isRetryBiometryDisplay(Boolean.valueOf(rdc))).isTrue();

    }

    @Rule
    public TestWatcher watchman= new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                System.out.println("Test Failed!");
                driver.executeScript("sauce:job-result=failed");
            } catch (Exception ignored) {
            } finally {
                driver.quit();
            }
        }

        @Override
        protected void succeeded(Description description) {
            try {
                System.out.println("Test Passed!");
                driver.executeScript("sauce:job-result=passed");
            } catch (Exception ignored) {
            } finally {
                driver.quit();
            }
        }
    };

    public boolean isBiometryDisplayed(){
        try {
            return driver.findElement(biometryButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }
    public void login(boolean successful) {
        try {

            WebElement biometryBtn = driver.findElement(biometryButton);
            biometryBtn.click();

            driver.performTouchID(successful);

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    public boolean isOnProductsPage() {

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        boolean isDisplay =  driver.findElementByIosNsPredicate(ProductTitleSelector).isDisplayed();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return isDisplay;
    }


    public boolean isRetryBiometryDisplay(boolean rdc){
        String elementText;

        try {
            if (rdc) {
                elementText = "Cancel";
            } else {
                elementText = "Try Again";
            }

            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementText)));

        } catch (TimeoutException e){
            return false;
        }
        return true;
    }

}
