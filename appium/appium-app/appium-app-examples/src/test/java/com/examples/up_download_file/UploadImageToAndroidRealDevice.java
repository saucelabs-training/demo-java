package com.examples.up_download_file;

import com.helpers.Constants;
import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.helpers.Constants.SAUCE_EU_URL;
import static com.helpers.Constants.SAUCE_US_URL;
import static org.assertj.core.api.Assertions.assertThat;

public class UploadImageToAndroidRealDevice {

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    protected AndroidDriver driver;


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
        sauceOptions.setCapability("build", "updateImage-job-1");
        List<String> tags = Arrays.asList( "Android","Update Image");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new AndroidDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void uploadFileToRealDevice() throws InterruptedException, IOException {
        System.out.println("Sauce - start test uploadFileToRealDevice");
        driver.context("NATIVE_APP");

        SamsungGallery samsungGallery = new SamsungGallery(driver);
        samsungGallery.open();

        int numOfPhotos = samsungGallery.amountOfPhotos();
        System.out.println("Sauce - number of photos before upload: " + numOfPhotos );

        //The magic happens here
        // The file we want to upload
        String codingBot = "src/test/java/com/appium_app/up_download_file/sauce-bot-coding.png";
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

}