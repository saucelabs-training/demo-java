package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    //We need to create a ThreadLocal object to be accessed by a specific thread
    private ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public WebDriver getDriver(){
        return threadLocalDriver.get();
    }

    public String baseUrl;
    public WebDriverWait pageWait;

    public BasePage(WebDriver driver) {
        threadLocalDriver.set(driver);
        pageWait = new WebDriverWait(getDriver(), 10);
        baseUrl = "https://www.saucedemo.com";
    }
}
