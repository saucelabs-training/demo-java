package com.realdevices;

import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;

public class StepDefinitions {
    private RemoteWebDriver driver;
    private WebDriverWait wait;

    @io.cucumber.java.Before
    public void setUp(Scenario scenario) throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("appiumVersion", "1.17.1");
        capabilities.setCapability("platformVersion", "13.2");
        capabilities.setCapability("deviceName", "iPhone 11 Pro Max");

        capabilities.setCapability("idleTimeout", "90");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("newCommandTimeout", "90");
        capabilities.setCapability("language", "en");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("name", scenario.getName());
        capabilities.setCapability("app",
                "storage:filename=" + "ios");

        driver = new IOSDriver(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        wait = new WebDriverWait(driver, 20);
    }

    @io.cucumber.java.After
    public void tearDown(Scenario scenario){
        driver.quit();
    }

    @Given("I open the iOS application")
    public void iOpenTheIOSApplication() {
        //do nothing
    }

    @Then("I should see Swag Labs login page")
    public void iShouldSeeSwagLabsLoginPage() {
        WebElement loginField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("test-LOGIN")));
        Assert.assertTrue("Should see login field", loginField.isDisplayed());
    }
}
