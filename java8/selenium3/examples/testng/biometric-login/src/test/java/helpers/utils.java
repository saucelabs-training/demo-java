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
