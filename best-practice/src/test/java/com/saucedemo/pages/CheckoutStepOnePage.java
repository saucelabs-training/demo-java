package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePage{
    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "checkout-step-one.html";
    }
}
