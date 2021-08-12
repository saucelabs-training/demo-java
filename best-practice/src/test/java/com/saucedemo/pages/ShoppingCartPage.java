package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Page Object representing shopping cart page.
 */
public class ShoppingCartPage extends AbstractBasePage {
    public ShoppingCartPage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "cart.html";
    }
}
