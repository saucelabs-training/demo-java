package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            return getDriver().findElement(By.className("app_logo")).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
