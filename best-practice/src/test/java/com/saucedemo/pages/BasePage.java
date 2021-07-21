package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class BasePage {
    public RemoteWebDriver driver;

    public BasePage(RemoteWebDriver driver) {
        this.driver = driver;
    }
    public void takeSnapshot() {
        driver.executeScript("/*@visual.snapshot*/",this.getClass().getSimpleName());
    }

    public void visit() {
        driver.get("https://www.saucedemo.com/" + getPagePart());
    }

    public abstract String getPagePart();
}
