package com.realdevices;

import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {
    private IOSDriver driver;
    private WebDriverWait wait;
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();
    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com:443/wd/hub";
    public static final String region = System.getProperty("region", "us");

    @io.cucumber.java.Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
        URL url;

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                break;
        }

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("automationName", "XCuiTest");
        //Allocate any avilable iPhone device with version 14
        capabilities.setCapability("appium:deviceName", "iPhone.*");
        capabilities.setCapability("appium:platformVersion", "14");
        //      You can use  storage:filename=" +appName if you uploaded your app to Saucd Storage
        //      capabilities.setCapability("app", "storage:filename=" +appName);
        capabilities.setCapability("appium:app",
                "https://github.com/saucelabs/my-demo-app-rn/releases/download/v.1.1.0-build-146-224/iOS-Real-Device-MyRNDemoApp.1.1.0-146.ipa");

        sauceOptions.setCapability("name", scenario.getName());
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
//        sauceOptions.setCapability("noReset", "true");
        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new IOSDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @io.cucumber.java.After
    public void tearDown(Scenario scenario){
        driver.quit();
    }

    @Given("I open the iOS application")
    public void iOpenTheIOSApplication() {
        //do nothing
    }

    @Then("I should see the home page")
    public void iShouldSeeSwagLabsLoginPage() {
        By productsScreenLocator = By.id("products screen");
        assertEquals(true, isDisplayed(productsScreenLocator, 10));
    }
    public Boolean isDisplayed(By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (org.openqa.selenium.TimeoutException exception) {
            return false;
        }
        return true;
    }
}
