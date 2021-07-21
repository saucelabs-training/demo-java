package com.saucedemo.selenium.cucumber;

import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.stream.IntStream;

public class StepDefinitions {
    private WebDriverWait wait;

    protected static ThreadLocal<SauceSession> session = new ThreadLocal<>();
    protected static ThreadLocal<SauceOptions> options = new ThreadLocal<>();

    public SauceSession getSession() {
        return session.get();
    }

    public RemoteWebDriver getDriver() {
        return getSession().getDriver();
    }


    @Before
    public void setUp(Scenario scenario) {
        options.set(new SauceOptions());
        options.get().setName(scenario.getName());

        if (System.getenv("START_TIME") != null) {
            options.get().setBuild("Build Time: " + System.getenv("START_TIME"));
        }

        String platform;
        if (System.getProperty("platform") != null) {
            platform = System.getProperty("platform");
        } else {
            platform = "default";
        }

        switch(platform) {
            case "windows_10_edge":
                options.get().setPlatformName(SaucePlatform.WINDOWS_10);
                options.get().setBrowserName(Browser.EDGE);
                break;
            case "mac_sierra_chrome":
                options.get().setPlatformName(SaucePlatform.MAC_SIERRA);
                options.get().setBrowserName(Browser.CHROME);
                break;
            case "windows_8_ff":
                options.get().setPlatformName(SaucePlatform.WINDOWS_8);
                options.get().setBrowserName(Browser.FIREFOX);
                break;
            case "windows_8_1_ie":
                options.get().setPlatformName(SaucePlatform.WINDOWS_8_1);
                options.get().setBrowserName(Browser.INTERNET_EXPLORER);
                break;
            case "mac_mojave_safari":
                options.get().setPlatformName(SaucePlatform.MAC_MOJAVE);
                options.get().setBrowserName(Browser.SAFARI);
                break;
            default:
                // accept Sauce defaults
                break;
        }

        session.set(new SauceSession(options.get()));

        getSession().start();
        wait = new WebDriverWait(getDriver(), 10);
    }

    @After
    public void tearDown(Scenario scenario){
        getSession().stop(!scenario.isFailed());
    }

    @Given("^I go to the login page$")
    public void go_to_login_page() {
        getDriver().get("https://www.saucedemo.com");
    }

    @Given("I am on the inventory page")
    public void go_to_the_inventory_page(){
        getDriver().get("https://www.saucedemo.com/inventory.html");
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
        getDriver().findElement(By.id("user-name")).sendKeys(username);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        getDriver().findElement(By.id("password")).sendKeys(password);

        getDriver().findElement(By.className("btn_action")).click();
    }

    @When("^I add (\\d+) items? to the cart$")
    public void add_items_to_cart(int items){
        By itemButton = By.className("btn_primary");

        IntStream.range(0, items).forEach(i -> {
            wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(itemButton)));
            getDriver().findElement(itemButton).click();
        });
    }

    @And("I remove an item")
    public void remove_an_item(){
        By itemButton = By.className("btn_secondary");

        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(itemButton)));
        getDriver().findElement(itemButton).click();
    }

    @Then("I have (\\d) items? in my cart")
    public void one_item_in_cart(Integer items) {
        String expected_items = items.toString();

        By itemsInCart = By.className("shopping_cart_badge");

        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(itemsInCart)));
        Assert.assertEquals(getDriver().findElement(itemsInCart).getText(), expected_items);
    }

    @Then("The item list is not displayed")
    public void item_list_is_not_diplayed() {
        Assert.assertEquals(getDriver().findElements(By.id("inventory_container")).size(), 0);
    }

    @Then("The item list is displayed")
    public void item_list_is_diplayed() {
        Assert.assertTrue(getDriver().findElement(By.id("inventory_container")).isDisplayed());
    }
}
