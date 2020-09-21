package com.saucedemo;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
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
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");

        ChromeOptions chromeOpts = new ChromeOptions();

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("name", scenario.getName());
        sauceOpts.setCapability("build", "Java-W3C-Examples");
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accessKey);

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY,  chromeOpts);
        caps.setCapability("sauce:options", sauceOpts);
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("platformName", "windows 10");

        String sauceUrl = "https://ondemand.saucelabs.com:443/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, caps);
        wait = new WebDriverWait(driver, 10);
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

    /**
     * Use this method to send any number of login/password parameters, to test different edge cases or roles within
     * the software. This method exists to show an example of how steps can call other parameterized methods.
     * @param username The user name to login with
     * @param password The password to use (for testing the password field
     */
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
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(itemButton)));
            driver.findElement(itemButton).click();
        });
    }

    @And("I remove an item")
    public void remove_an_item(){
        By itemButton = By.className("btn_secondary");

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(itemButton)));
        driver.findElement(itemButton).click();
    }

    @Then("I have {int} item in my cart")
    public void i_have_item_in_my_cart(Integer items) {
        String expected_items = items.toString();

        By itemsInCart = By.className("shopping_cart_badge");

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(itemsInCart)));
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
