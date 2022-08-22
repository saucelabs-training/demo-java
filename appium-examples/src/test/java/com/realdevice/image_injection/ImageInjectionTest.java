package com.realdevice.image_injection;


import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static helpers.Constants.region;

public class ImageInjectionTest {

    @Rule
    public TestName name = new TestName();

    String testMenu = "open menu";
    String testMenuItemQRCode ="menu item qr code scanner";

    String ios_testMenu = "tab bar option menu";


    protected AndroidDriver androidDriver;
    protected IOSDriver iOSDriver;

    MutableCapabilities capabilities;
    URL url;

    @Before
    public void setup() throws IOException {
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceUrl;

        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }
        String remoteUrl = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        url = new URL(remoteUrl);

        capabilities = new MutableCapabilities();
        capabilities.setCapability("name", name.getMethodName());

        //this is what you need to add to your capabilities for image injection
        capabilities.setCapability("resigningEnabled", true);
        capabilities.setCapability("sauceLabsImageInjectionEnabled", true);

    }

    @Test
    public void imageInjectAndroid() throws InterruptedException {
        capabilities.setCapability("deviceName", "Samsung Galaxy S10");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");

//      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
//        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("app", "https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.0.0-build-61-123/Android-MyDemoAppRN.1.0.0.build-130.apk");
        capabilities.setCapability("appWaitActivity", "com.saucelabs.mydemoapp.rn.MainActivity");
        capabilities.setCapability("autoGrantPermissions", true);
        androidDriver = new AndroidDriver(url, capabilities);

        WebDriverWait wait = new WebDriverWait(androidDriver, Duration.ofSeconds(10));

        // *** The main test *****

        // Select QR Code Scanner from the menu

        //wait for the product field to be visible and store that element into a variable
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(testMenu)));
        menu.click();

        // *** selec Menu QR Code
        WebElement qCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(testMenuItemQRCode)));
        qCCodeMenu.click();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/java/com/realdevice/image_injection/images/qr-code.png");
        androidDriver.executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isAndroidBrowserOpened();

        // This is not need only for the video
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        androidDriver.quit();
    }

    @Test
    public void imageInjectIOS() throws InterruptedException {
        capabilities.setCapability("deviceName", "iPhone 8*");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCUITEST");

//      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
//        capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("app", "https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.0.0-build-61-123/iOS-Real-Device-MyRNDemoApp.1.0.0-67.ipa");
        capabilities.setCapability("autoAcceptAlerts", true);

        iOSDriver = new IOSDriver(url, capabilities);

        WebDriverWait wait = new WebDriverWait(iOSDriver, Duration.ofSeconds(10));

        // *** The main test *****

        // Select QR Code Scanner from the menu

        //wait for the product field to be visible and store that element into a variable
        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(ios_testMenu)));
        menu.click();

        // *** selec Menu QR Code
        WebElement qCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(testMenuItemQRCode)));
        qCCodeMenu.click();

        // inject the image - provide the transformed image to the device with this command
        String qrCodeImage = encoder("src/test/java/com/realdevice/image_injection/images/qr-code.png");
        iOSDriver.executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isIosApplicationRunning("com.apple.mobilesafari");

        // This is not need only for the video
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        iOSDriver.quit();
    }


    // Android
    public boolean isAndroidBrowserOpened() throws InterruptedException {
        int wait = 8;
        int counter = 0;

        do {
            if (!androidDriver.currentActivity().contains(".MainActivity")){
                return true;
            }
            Thread.sleep(500);
            counter++;
        } while(counter<=wait);

        return false;
    }

    // ios
    public long getIosAppState(String bundleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", bundleId);

        // App state: 0 is not installed. 1 is not running. 2 is running in background or suspended. 3 is running in background. 4 is running in foreground. (number)
        long res = (long) iOSDriver.executeScript("mobile:queryAppState", params);

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
