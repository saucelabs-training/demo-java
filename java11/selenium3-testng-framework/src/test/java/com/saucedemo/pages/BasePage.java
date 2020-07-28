package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    public final WebDriver driver;
    public String baseUrl;
    public WebDriverWait pageWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        pageWait = new WebDriverWait(this.driver, 10);
        baseUrl = "https://www.saucedemo.com";
    }
}
