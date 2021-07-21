package com.realdevice.unifiedplatform.image_injection;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Utils {

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

    public boolean isAndroidBrowserOpened(AndroidDriver driver) throws InterruptedException {
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

    public long getIosAppState(IOSDriver driver, String bundleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", bundleId);

        // App state: 0 is not installed. 1 is not running. 2 is running in background or suspended. 3 is running in background. 4 is running in foreground. (number)
        long res = (long) driver.executeScript("mobile:queryAppState", params);

        System.out.println("Sauce. iOS App state for bundleId " + bundleId + " is " + res );
        return res;
    }

    public boolean isIosApplicationRunning(IOSDriver driver, String bundleId) throws InterruptedException{

        int wait = 8;
        int counter = 0;

        do {
            if (getIosAppState(driver, bundleId) == 4){
                return true;
            }
            Thread.sleep(500);
            counter++;
        } while(counter<=wait);

        return false;
    }

    public boolean isIOS(AppiumDriver driver){
        if (driver.getPlatformName().toLowerCase().equals("ios")) {
            System.out.println("Sauce. iOS platform");
            return true;
        }

        System.out.println("Sauce. Android platform");
        return false;
    }

}
