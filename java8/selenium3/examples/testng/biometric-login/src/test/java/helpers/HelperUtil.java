package helpers;

import java.io.*;
import java.util.Properties;

public class HelperUtil {

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
