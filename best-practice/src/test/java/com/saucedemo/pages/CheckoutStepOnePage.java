package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public class CheckoutStepOnePage extends BasePage{
    public CheckoutStepOnePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "checkout-step-one.html";
    }
}
