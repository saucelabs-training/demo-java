package com.yourcompany.pages;

import org.openqa.selenium.WebDriver;

public class BasePO {
    protected WebDriver driver;

    public BasePO(){

    }
    public BasePO(WebDriver driver) {
        this.driver = driver;
    }

}
