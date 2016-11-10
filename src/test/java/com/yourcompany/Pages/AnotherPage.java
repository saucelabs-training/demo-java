package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;

public class AnotherPage extends PageBase {

    public AnotherPage(WebDriver driver) {
        this.driver = driver;
    }

    public String title = "I am another page title - Sauce Labs";

    public static String getTitle(WebDriver driver) {
        return driver.getTitle();
    }
}
