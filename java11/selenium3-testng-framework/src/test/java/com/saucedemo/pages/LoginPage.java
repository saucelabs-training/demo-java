package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage visit() {
        getDriver().get(baseUrl);
        return this;
    }

    public InventoryPage login(String username, String password) {
        String userField = "[data-test='username']";
        String passField = "[data-test='password']";
        String loginBtn = "[value='LOGIN']";

        getDriver().findElement(By.cssSelector(userField)).sendKeys(username);
        getDriver().findElement(By.cssSelector(passField)).sendKeys(password);
        getDriver().findElement(By.cssSelector(loginBtn)).click();
        return new InventoryPage(getDriver());
    }

    public boolean isLoaded() {
        WebElement sauceBot = getDriver().findElement(By.className("bot_column"));
        return pageWait.until(ExpectedConditions.visibilityOf(sauceBot)).isDisplayed();
    }
}
