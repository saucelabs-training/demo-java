package com.examples.image_injection;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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

import static com.helpers.Constants.*;

public class ImageInjectionAndroidTest {

    @Rule
    public TestName name = new TestName();

    // This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    String testMenu = "View menu";
    String testMenuItemQRCode = "menu item qr code scanner";

    protected AndroidDriver driver;

    MutableCapabilities capabilities;
    MutableCapabilities sauceOptions;
    URL url;

    @Before
    public void setup() throws IOException {

        System.out.println("Sauce Image Injection  - BeforeMethod hook");

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

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("appium:app", "storage:filename=SauceLabs-Demo-App.apk");
        caps.setCapability("appium:deviceName", "Samsung.*");
        caps.setCapability("appium:platformVersion", "14");
        caps.setCapability("appium:automationName", "UiAutomator2");
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("appiumVersion", "latest");
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.setCapability("build", "imageInjection-job-1");
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("resigningEnabled", true);
        sauceOptions.setCapability("networkCapture", true);
        sauceOptions.setCapability("imageInjection", true);

        caps.setCapability("sauce:options", sauceOptions);

        // Start the session
        URL url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
        try {
            driver = new AndroidDriver(url, caps);
        } catch (Exception e) {
            System.out.println("Error to create Android Driver: " + e.getMessage());
        }

        // Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void imageInjectionAndroidAcceptInTestV14() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Select QR Code Scanner from the menu

        // wait for the product field to be visible and store that element into a
        // variable
        WebElement menu = wait
                .until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("View menu")));
        menu.click();

        // *** select Menu QR Code
        WebElement qCCodeMenu = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='QR Code Scanner']")));
        qCCodeMenu.click();

        // Handle the alert
        Alert alert = driver.switchTo().alert();
        // String alertText = alert.getText();
        alert.accept();
        // alert.dismiss();

        // inject the image - provide the transformed image to the device with this
        // command
        String qrCodeImage = encoder("src/test/java/com/examples/image_injection/images/qr-code.png");

        driver.executeScript("sauce:inject-image=" + qrCodeImage);

        // Verify that the browser is running
        isAndroidBrowserOpened();

        // For demo purpose so we can see the image injection in the movie
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // Android
    public boolean isAndroidBrowserOpened() throws InterruptedException {
        int wait = 8;
        int counter = 0;

        do {
            if (!driver.currentActivity().contains(".MainActivity")) {
                return true;
            }
            Thread.sleep(500);
            counter++;
        } while (counter <= wait);

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
