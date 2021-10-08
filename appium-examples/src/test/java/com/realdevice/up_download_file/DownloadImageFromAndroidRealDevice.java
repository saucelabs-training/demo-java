package com.realdevice.up_download_file;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.MutableCapabilities;
import org.springframework.util.FileSystemUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static helpers.Constants.region;
import static org.assertj.core.api.Assertions.assertThat;

public class DownloadImageFromAndroidRealDevice {

    @Rule
    public TestName name = new TestName();

    protected AndroidDriver driver;

    int currentPhotos = 0;
    String deviceFilePath = "/storage/self/primary/sauce-bot-coding.png";
    String downloadFolder = "src/test/java/com/realdevice/up_download_file/samsung_real_device";


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
        // This is a specific device type so I can control the state of it
        capabilities.setCapability("deviceName", "Samsung Galaxy S9");
        capabilities.setCapability("platformVersion", "10");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("name", name.getMethodName());
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("autoGrantPermissions", true);

        // Launch remote browser and set it as the current thread
        driver = new AndroidDriver(url, capabilities);
    }

    @Test
    public void downloadFileFromRealDevice() throws InterruptedException, IOException {
        System.out.println("Sauce - start test downloadFileFromRealDevice");
        driver.context("NATIVE_APP");


        // Make sure the download dir we are going to use is empty
        File downloadFolderDir = new File(downloadFolder);
        FileUtils.deleteDirectory(downloadFolderDir);
        // Create the directory
        FileUtils.forceMkdir(downloadFolderDir);

        // Start the Gallery on the device
        SamsungGallery samsungGallery = new SamsungGallery(driver);
        samsungGallery.open();
        currentPhotos = samsungGallery.amountOfPhotos();
        System.out.println("Sauce - number of photos before upload: " + currentPhotos );

        // The file we want to upload
        String codingBot = "src/test/java/com/realdevice/up_download_file/sauce-bot-coding.png";
        File codingBotFile = new File(codingBot);
        // Push the file to the device
        // This is the `tricky` part, you need to know the file structure of the device and where you can download the file from.
        // We checked this structure with the VUSB offering of Sauce Labs for private devices.
        driver.pushFile(deviceFilePath, codingBotFile);

        // wait till it is uploaded
        boolean bPhotoUpload = samsungGallery.waitUploadPhoto(currentPhotos, 5);

        // This is not need only for the video
        waiting(2);

        // Pull the file from the device, it was uploaded in the before step
        byte[] downloadedBase64Image = driver.pullFile(deviceFilePath);

        // Before continue with the downloaded file -> delete the file from the device
        samsungGallery.deletePhoto("last");

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(downloadedBase64Image));
        String downloadFile = downloadFolder + "/downloaded-sauce-bot-coding.png";
        File f = new File(downloadFile);

        // write the image
        ImageIO.write(image, "png", f);

        // Now verify that the file does exist locally
        assertThat(f.exists()).as("The file we downloaded from the device, doesm't exist locally").isTrue();
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