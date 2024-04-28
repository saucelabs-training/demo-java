package com.examples.find_by_image;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static com.helpers.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Android Native App Tests
 */
public class AndroidFindByImageTest {

    By productsScreenLocator = By.id("com.saucelabs.mydemoapp.android:id/productTV");
    By productItemTitleLocator = By.id("com.saucelabs.mydemoapp.android:id/productTV");


    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Sauce Android Native App  - Before hook");
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

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

        // For all capabilities please check
        // https://appium.io/docs/en/2.0/guides/caps/
        // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
        // to find the emulators/real devices names, OS versions and appium versions you can use for your testings
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        if (rdc.equals("true")) {
            capabilities.setCapability("appium:deviceName", "^Samsung.*");
            capabilities.setCapability("appium:platformVersion", "1[2-4]");
            sauceOptions.setCapability("resigningEnabled", true);
            sauceOptions.setCapability("sauceLabsNetworkCaptureEnabled", true);
        }
        else {
            capabilities.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
            capabilities.setCapability("appium:platformVersion", "13");
        }
        String appName = "SauceLabs-Demo-App.apk";
        capabilities.setCapability("appium:app", "storage:filename=" +appName);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("appiumVersion", "latest");
        sauceOptions.setCapability("build", "myApp-job-findImage-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "FindByImage");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        capabilities.setCapability("sauce:options", sauceOptions);

        MutableCapabilities settingsOptions = new MutableCapabilities();
        settingsOptions.setCapability("imageMatchThreshold", 0.4);
        settingsOptions.setCapability("getMatchedImageResult", true);

        capabilities.setCapability("appium:settings", settingsOptions);

        try {
            driver = new AndroidDriver(url, capabilities);
        } catch (Exception e){
            System.out.println("Error to create Android Driver: " + e.getMessage());
            return;
        }
        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void verifySelectingBackPackProduct() throws MalformedURLException {
        //Wait for the application to start and load the initial screen (products screen)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsScreenLocator));

        // find the Backpack product by image
        // Please read about that here: https://github.com/appium/appium/blob/master/packages/images-plugin/docs/find-by-image.md?utm_source=beamer&utm_medium=sidebar&utm_campaign=Now-available-Appium-Image-Selectors-and-Elements-support-on-Real-Device-Cloud&utm_content=ctalink
        // And here: https://changelog.saucelabs.com/en/now-available-appium-image-selectors-and-elements-support-on-real-device-cloud-qMPpYR6O
        String backpackImage = encoder("src/test/java/com/examples/find_by_image/androidBackpackImage.png");
        WebElement backpackImageElement =  driver.findElement(AppiumBy.image(backpackImage));

        // Start **********************************************************************
        // No need to do it. Only if you want to see the image that Appium capture
        // The image is saved as an image file "output_image.png"
        String returnImage = backpackImageElement.getAttribute("visual");
        // Decode Base64 string to image bytes
        byte[] imageBytes = Base64.getDecoder().decode(returnImage);

        // Convert byte array into BufferedImage
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);

            // Write the image to a file
            File outputFile = new File("src/test/java/com/examples/find_by_image/output_image.png");
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image has been written successfully");
        } catch (IOException e) {
            System.err.println("Unable to convert Base64 string to image");
        }
        // End ************************************************************************

        // Click on the image
        backpackImageElement.click();

        //Verify we are in the backpack product item page
        assertThat(isTextAsExpected(productItemTitleLocator, "Backpack", 5)).as("Verify product item title").isTrue();
    }

    public Boolean isTextAsExpected(By locator, String expectedText ,long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.attributeContains(locator, "text", expectedText ));
        } catch (org.openqa.selenium.TimeoutException exception) {
            // Report the error to Sauce log
            driver.executeScript("sauce:context=Error - The title is not " + expectedText);
            return false;
        }
        return true;
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
