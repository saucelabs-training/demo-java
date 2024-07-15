package com.examples.simple_example;

import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.ios.IOSDriver;
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

public class IOSWebAppTest {

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private IOSDriver driver;
    By usernameInput = By.id("user-name");
    By passwordInput = By.id("password");
    By submitButton = By.className("btn_action");
    By inventoryList = By.className("inventory_list");

    @Before
    public void setUp() throws MalformedURLException {
        System.out.println("Sauce iOS Native App  - Before hook");

        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;
        String appName;

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                break;
        }

        // For all capabilities please check
        // http://appium.io/docs/en/writing-running-appium/caps/#general-capabilities
        // Use the platform configuration https://saucelabs.com/platform/platform-configurator#/
        // to find the simulators/real device names, OS versions and appium versions you can use for your testings

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCuiTest");
        if (rdc.equals("true")) {
            //Allocate any avilable iPhone device with version 14
            capabilities.setCapability("appium:deviceName", "iPhone.*");
            capabilities.setCapability("appium:platformVersion", "14");
        }
        else {
            capabilities.setCapability("appium:deviceName", "iPhone Instant Simulator");
            capabilities.setCapability("appium:platformVersion", "current_major");
        }
        capabilities.setCapability("browserName", "safari");

        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "myWebApp-job-1");
        List<String> tags = Arrays.asList("sauceDemo_ios", "iOS", "Demo", "Web");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        capabilities.setCapability("sauce:options", sauceOptions);

        try {
        driver = new IOSDriver(url, capabilities);
        } catch (Exception e){
            System.out.println("Error to create iOS Driver: " + e.getMessage());
        }

        //Setting the driver so that we can report results
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
        // Verificsation
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
