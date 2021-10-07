package com.realdevice.up_download_file;

import com.realdevice.image_injection.Utils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static helpers.Constants.region;
import static org.assertj.core.api.Assertions.assertThat;

public class UploadImageAndroidRealDevice {

    @Rule
    public TestName name = new TestName();

    protected AndroidDriver driver;


    @Before
    public void setup() throws IOException {

        System.out.println("Sauce - BeforeEach hook");

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        String sauceUrl;
        if (region.equalsIgnoreCase("eu")) {
            sauceUrl = "@ondemand.eu-central-1.saucelabs.com:443";
        } else {
            sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        }
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        URL url = new URL(SAUCE_REMOTE_URL);

        MutableCapabilities capabilities = new MutableCapabilities();
        // This is a specific private device so I can control the state of it
        capabilities.setCapability("deviceName", "Samsung_Galaxy_S9_POC24");

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("name", name.getMethodName());
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("autoGrantPermissions", true);

        // Launch remote browser and set it as the current thread
        driver = new AndroidDriver(url, capabilities);
    }

    @Test
    public void uploadPhotoToTheGallery() throws InterruptedException, IOException {
        System.out.println("Sauce - start test uploadPhotoToTheGallery");
        driver.context("NATIVE_APP");

        SamsungGallery samsungGallery = new SamsungGallery(driver);
        samsungGallery.open();

        int numOfPhotos = samsungGallery.amountOfPhotos();
        System.out.println("Sauce - number of photos before upload: " + numOfPhotos );

        //The magic happens here
        // The file we want to upload
        String codingBot = "src/test/java/com/realdevice/up_download_file/sauce-bot-coding.png";
        File codingBotFile = new File(codingBot);
        // Push the file to the device
        // This is the `tricky` part, you need to know the file structure of the device and where you can download the file from.
        // We checked this structure with the VUSB offering of Sauce Labs for private devices.
        driver.pushFile("/storage/self/primary/sauce-bot-coding.png", codingBotFile);

        // wait till it is uploaded
        boolean bPhotoUpload = samsungGallery.waitUploadPhoto(numOfPhotos, 5);
        // Verification the photo was uploaded
        Assert.assertTrue("Failed to upload a photo to the device", bPhotoUpload);

        // This is not need only for the video
        waiting(2);

        // Delete the photo and verify that the amount of photos is equal to when the test started
        samsungGallery.deletePhoto("last");
        // Verification
        assertThat((samsungGallery.amountOfPhotos())).isEqualTo(numOfPhotos);
        // This is not need only for the video
        waiting(2);
    }

    public static void waiting(int sec){
        try
        {
            Thread.sleep(sec*1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    @Rule
    public TestWatcher watchman= new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            try {
                System.out.println("Test Failed! ");
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

}