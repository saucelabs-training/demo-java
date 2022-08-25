package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Page Object for Login page.
 */
public class LoginPage extends AbstractBasePage {
    private final By usernameFieldLocator = By.id("user-name");
    private final By passwordFieldLocator = By.id("password");
    private final By submitButtonLocator = By.id("login-button");

    public LoginPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "";
    }

    /**
     * Log in on page.
     *
     * @param userName the name of the user to log in
     */
    public void login(String userName) {
        //Create an instance of a Selenium explicit wait to dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait for the user name field to be visible and store that element into a variable
        wait.until((driver) -> driver.findElement(usernameFieldLocator).isDisplayed());

        WebElement userNameField = driver.findElement(usernameFieldLocator);
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        WebElement submitButton = driver.findElement(submitButtonLocator);

        userNameField.sendKeys(userName);
        passwordField.sendKeys("secret_sauce");
        submitButton.click();
    }

    /**
     * How long it takes to load the page.
     *
     * @return duration of time to load the page
     */
    @SuppressWarnings("unchecked")
    public Integer getPageLoadTime() {
        HashMap<String, Object> metrics = new HashMap<>();
        metrics.put("type", "sauce:performance");
        Map<String, Object> perfMetrics = (Map<String, Object>) driver.executeScript("sauce:log", metrics);
        return Integer.parseInt(perfMetrics.get("load").toString());
    }

}
