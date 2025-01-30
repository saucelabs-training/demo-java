package com.examples.simple_example;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.helpers.Constants.*;

/**
 * Android Native App Tests
 */
public class AndroidWebAppTest {
    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private AndroidDriver driver;

    By usernameInput = By.id("user-name");
    By passwordInput = By.id("password");
    By submitButton = By.className("btn_action");
    By inventoryList = By.className("inventory_list");

    @Before
    public void setup() throws MalformedURLException {
        System.out.println("Sauce Android Web App  - Before hook");

        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

        url = switch (region) {
            case "us" -> new URL(SAUCE_US_URL);
            default -> new URL(SAUCE_EU_URL);
        };

        // For all capabilities please check
        // http://appium.io/docs/en/writing-running-appium/caps/#general-capabilities
        // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
        // to find the emulators/real devices names, OS versions and appium versions you can use for your testings
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        if (rdc.equals("true")) {
            capabilities.setCapability("appium:deviceName", "samsung.*");
            sauceOptions.setCapability("appiumVersion", "latest");
        }
        else {
            capabilities.setCapability("appium:deviceName", "Android GoogleAPI Emulator");
            sauceOptions.setCapability("appiumVersion", "latest");
        }

        capabilities.setCapability("appium:platformVersion", "13");
        capabilities.setCapability("browserName", "chrome");

        // Sauce capabilities
        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myWebApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "Android", "Demo", "Web");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new AndroidDriver(url, capabilities);

        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void loginToSwagLabsWebTestValid() {
        System.out.println("Sauce - Start loginToSwagLabsWebTestValid test");
        String urlToTest = "https://www.saucedemo.com/";
        driver.get(urlToTest);

        login("standard_user", "secret_sauce");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isOnProductsPage());
    }
    public void login(String user, String pass){
        driver.findElement(usernameInput).sendKeys(user);
        driver.findElement(passwordInput).sendKeys(pass);
        driver.findElement(submitButton).click();
    }

    public boolean isOnProductsPage() {
        return driver.findElement(inventoryList).isDisplayed();
    }
}
