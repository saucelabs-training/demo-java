package com.examples.image_injection;


import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.helpers.Constants.*;

public class ImageInjectionAndroidTest {

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    String testMenu = "open menu";
    String testMenuItemQRCode ="menu item qr code scanner";

    protected AndroidDriver driver;

    MutableCapabilities capabilities;
    MutableCapabilities sauceOptions;
    URL url;

    @Before
    public void setup() throws IOException {

        capabilities = new MutableCapabilities();
        sauceOptions = new MutableCapabilities();

        System.out.println("Sauce Image Injection  - BeforeMethod hook");

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                System.out.println("Sauce REGION US");
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                System.out.println("Sauce REGION EU");
                break;
        }

        //find a device in the cloud
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        //Allocate any avilable samsung device with Android version 12
        capabilities.setCapability("appium:deviceName", "Samsung.*");
        capabilities.setCapability("appium:platformVersion", "12");
        String appName = "Android.MyDemoAppRN.apk";
        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("appium:appWaitActivity","com.saucelabs.mydemoapp.rn.MainActivity");
        capabilities.setCapability("autoGrantPermissions", true);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "imageInjection-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android","Image Injection");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        sauceOptions.setCapability("resigningEnabled", true);
        sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
        sauceOptions.setCapability("sauceLabsImageInjectionEnabled", true);

        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            driver = new AndroidDriver(url, capabilities);
        } catch (Exception e){
            System.out.println("Error to create Android Driver: " + e.getMessage());
        }

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void imageInjectionAndroid() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Select QR Code Scanner from the menu

        //wait for the product field to be visible and store that element into a variable
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(testMenu)));
        menu.click();

        // *** select Menu QR Code
        WebElement qCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(testMenuItemQRCode)));
        qCCodeMenu.click();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/java/com/appium_app/image_injection/images/qr-code.png");
        driver.executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isAndroidBrowserOpened();

        // For demo purpose so we can see the image injection in the movie
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    // Android
    public boolean isAndroidBrowserOpened() throws InterruptedException {
        int wait = 8;
        int counter = 0;

        do {
            if (!driver.currentActivity().contains(".MainActivity")){
                return true;
            }
            Thread.sleep(500);
            counter++;
        } while(counter<=wait);

        return false;
    }


    public String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }
}
