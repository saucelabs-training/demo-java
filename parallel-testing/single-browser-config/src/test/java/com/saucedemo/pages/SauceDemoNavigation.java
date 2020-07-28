package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SauceDemoNavigation {
    private WebDriver driver;
    public SauceDemoNavigation(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage goToLoginPage() {
        LoginPage loginPage = new LoginPage();
        PageFactory.initElements(driver, loginPage);
        driver.get(loginPage.getUrl());
        return loginPage;
    }

    public InventoryPage getInventoryPage() {
        InventoryPage inventoryPage = new InventoryPage();
        PageFactory.initElements(driver, inventoryPage);
        return inventoryPage;
    }
}
