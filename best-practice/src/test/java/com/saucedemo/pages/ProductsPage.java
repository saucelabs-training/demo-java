package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object representing Products page.
 */
public class ProductsPage extends AbstractBasePage {

    public ProductsPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "inventory.html";
    }

    /**
     * Whether the browser is on the correct page.
     *
     * @return true if browser is on expected page
     */
    public boolean isDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait for the user name field to be visible and store that element into a variable
        By userNameFieldLocator = By.id("inventory_container");
        return
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator)).isDisplayed();
    }
}
