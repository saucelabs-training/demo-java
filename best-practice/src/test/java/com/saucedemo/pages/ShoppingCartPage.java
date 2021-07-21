package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public class ShoppingCartPage extends BasePage {
    public ShoppingCartPage(RemoteWebDriver driver) {
        super(driver);
    }
    @Override
    public String getPagePart() {
        return "cart.html";
    }
}
