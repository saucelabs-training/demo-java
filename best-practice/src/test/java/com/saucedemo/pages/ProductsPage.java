package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage extends BasePage {

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "inventory.html";
    }

    public boolean isDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //wait for the user name field to be visible and store that element into a variable
        By userNameFieldLocator = By.id("inventory_container");
        return
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator)).isDisplayed();
    }
}
