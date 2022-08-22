package com.realdevice.image_injection;


import com.realdevice.SauceTestWatcher;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
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
import java.util.*;

import static helpers.Constants.region;

public class ImageInjectionIosTest {

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    String testMenu = "open menu";
    String testMenuItemQRCode ="menu item qr code scanner";

    String ios_testMenu = "tab bar option menu";

    protected IOSDriver driver;

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

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "imageInjection-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Image Injection");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        sauceOptions.setCapability("resigningEnabled", true);
        sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
        sauceOptions.setCapability("sauceLabsImageInjectionEnabled", true);

        capabilities.setCapability("sauce:options", sauceOptions);

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCuiTest");
        //Allocate any avilable iPhone device with version 14
        capabilities.setCapability("appium:deviceName", "iPhone.*");
        capabilities.setCapability("appium:platformVersion", "14");
        String appName = "iOS-Real-Device-MyRNDemoApp.ipa";
        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("autoAcceptAlerts", true);

        driver = new IOSDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void imageInjectIOS() throws InterruptedException {

        // Select QR Code Scanner from the menu

        //wait for the product field to be visible and store that element into a variable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(ios_testMenu)));
        menu.click();

        // *** selec Menu QR Code
        WebElement qCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(testMenuItemQRCode)));
        qCCodeMenu.click();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/java/com/realdevice/image_injection/images/qr-code.png");
        driver.executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isIosApplicationRunning("com.apple.mobilesafari");

        // This is not need only for the video
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

    }

    // ios
    public long getIosAppState(String bundleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", bundleId);

        // App state: 0 is not installed. 1 is not running. 2 is running in background or suspended. 3 is running in background. 4 is running in foreground. (number)
        long res = (long) driver.executeScript("mobile:queryAppState", params);

        System.out.println("Sauce. iOS App state for bundleId " + bundleId + " is " + res );
        return res;
    }

    public boolean isIosApplicationRunning(String bundleId) throws InterruptedException{

        int wait = 8;
        int counter = 0;

        do {
            if (getIosAppState(bundleId) == 4){
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
