package com.realdevices;

import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.IntStream;

public class StepDefinitions {
    private WebDriver driver;
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
        capabilities.setCapability("app", "storage:filename=" + "iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.3.0.ipa");

        driver = new IOSDriver(
                new URL("https://" + System.getenv("SAUCE_USERNAME") + ":" +
                        System.getenv("SAUCE_ACCESS_KEY") +
                        "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities);
        wait = new WebDriverWait(driver, 20);
    }

    @io.cucumber.java.After
    public void tearDown(Scenario scenario){
        driver.quit();
    }

    @Given("^I go to the login page$")
    public void go_to_login_page() {
        driver.get("https://www.saucedemo.com");
    }

    @Given("I am on the inventory page")
    public void go_to_the_inventory_page(){
        driver.get("https://www.saucedemo.com/inventory.html");
    }

    @When("I login as a valid user")
    public void login_as_valid_user() {
        login("standard_user", "secret_sauce");
    }

    @When("I login as an invalid user")
    public void login_as_invalid_user() {
        login("doesnt_exist", "secret_sauce");
    }

    private void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        driver.findElement(By.id("user-name")).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        driver.findElement(By.id("password")).sendKeys(password);

        driver.findElement(By.className("btn_action")).click();
    }

    @When("^I add (\\d+) items? to the cart$")
    public void add_items_to_cart(int items){
        By itemButton = By.className("btn_primary");

        IntStream.range(0, items).forEach(i -> {
            wait.until(ExpectedConditions.
                    elementToBeClickable(itemButton));
            driver.findElement(itemButton).click();
        });
    }

    @And("I remove an item")
    public void remove_an_item(){
        By itemButton = By.className("btn_secondary");

        wait.until(ExpectedConditions.elementToBeClickable(itemButton));
        driver.findElement(itemButton).click();
    }

    @Then("I have {int} item in my cart")
    public void i_have_item_in_my_cart(Integer items) {
        String expected_items = items.toString();

        By itemsInCart = By.className("shopping_cart_badge");

        wait.until(ExpectedConditions.elementToBeClickable(itemsInCart));
        Assert.assertEquals(driver.findElement(itemsInCart).getText(), expected_items);
    }

    @Then("The item list is not displayed")
    public void item_list_is_not_diplayed() {
        Assert.assertEquals(driver.findElements(By.id("inventory_container")).size(), 0);
    }

    @Then("The item list is displayed")
    public void item_list_is_diplayed() {
        Assert.assertTrue(driver.findElement(By.id("inventory_container")).isDisplayed());
    }
}
