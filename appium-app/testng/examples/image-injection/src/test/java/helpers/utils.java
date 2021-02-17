package helpers;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class utils {

    public static String encoder(String imagePath) {
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

    public static boolean isAndroidBrowserOpened(AndroidDriver driver) throws InterruptedException {
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

    public static long getIosAppState(IOSDriver driver, String bundleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", bundleId);

        // App state: 0 is not installed. 1 is not running. 2 is running in background or suspended. 3 is running in background. 4 is running in foreground. (number)
        long res = (long) ((JavascriptExecutor) driver).executeScript("mobile:queryAppState", params);

        System.out.println("Sauce. iOS App state for bundleId " + bundleId + " is " + res );
        return res;
    }

    public static boolean isIosApplicationRunning(IOSDriver driver, String bundleId) throws InterruptedException{

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

    public static boolean isIOS(AppiumDriver driver){
        if (driver.getPlatformName().toLowerCase().equals("ios")) {
            System.out.println("Sauce. iOS platform");
            return true;
        }

        System.out.println("Sauce. Android platform");
        return false;
    }

    public static String getProperty(String propertyName, String defaultVal){
        String propertyValue = defaultVal;
        try {
            InputStream input = new FileInputStream(System.getProperties().get("basedir") + "/target/project.properties");
            Properties prop = new Properties();
            prop.load(input);
            propertyValue = prop.getProperty(propertyName);
            System.out.println("Sauce - value for " + propertyName + " is: " + propertyValue);
        } catch (IOException ex) {
            // The file will be created only when execute using maven. When debugging - the file will not be created and prop will be null
            System.out.println("Sauce - Can't create the prop file");
        }

        return propertyValue;
    }
}
