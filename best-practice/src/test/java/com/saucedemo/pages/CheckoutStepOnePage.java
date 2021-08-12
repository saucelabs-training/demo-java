package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Page Object for Checkout Step One.
 */
public class CheckoutStepOnePage extends AbstractBasePage {
    public CheckoutStepOnePage(RemoteWebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "checkout-step-one.html";
    }
}
