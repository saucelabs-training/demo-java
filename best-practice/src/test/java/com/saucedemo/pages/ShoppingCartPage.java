package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

public class ShoppingCartPage extends BasePage {
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }
    @Override
    public String getPagePart() {
        return "cart.html";
    }
}
