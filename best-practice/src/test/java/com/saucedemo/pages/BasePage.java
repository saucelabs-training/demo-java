package com.saucedemo.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public abstract class BasePage {
    public WebDriver driver;
    JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        js = (JavascriptExecutor) driver;
    }
    public void takeSnapshot() {
        js.executeScript("/*@visual.snapshot*/",this.getClass().getSimpleName());
    }

    public void visit() {
        driver.get("https://www.saucedemo.com/" + getPagePart());
    }

    public abstract String getPagePart();
}
