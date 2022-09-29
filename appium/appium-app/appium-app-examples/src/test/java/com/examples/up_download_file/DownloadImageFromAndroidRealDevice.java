package com.examples.up_download_file;

import com.helpers.Constants;
import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.helpers.Constants.SAUCE_EU_URL;
import static com.helpers.Constants.SAUCE_US_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class DownloadImageFromAndroidRealDevice {

    @Rule
    public TestName name = new TestName();

    protected AndroidDriver driver;

    int currentPhotos = 0;
    String deviceFilePath = "/storage/self/primary/sauce-bot-coding.png";
    String downloadFolder = "src/test/java/com/appium_app/up_download_file/samsung_real_device";

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    @Before
    public void setup() throws IOException {
        System.out.println("Sauce - BeforeEach hook");

        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

        switch (Constants.region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                break;
        }

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:deviceName", "Samsung Galaxy S9");
        capabilities.setCapability("appium:platformVersion", "10");
        capabilities.setCapability("appium:newCommandTimeout", 240);
        capabilities.setCapability("appium:browserName", "chrome");
        capabilities.setCapability("appium:autoGrantPermissions", true);

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "downloadImage-job-1");
        List<String> tags = Arrays.asList( "Android","Download Image");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new AndroidDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
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
        String codingBot = "src/test/java/com/appium_app/up_download_file/sauce-bot-coding.png";
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

}