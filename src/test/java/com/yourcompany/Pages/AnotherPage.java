package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class AnotherPage extends PageBase {

    public String title = "I am another page title - Sauce Labs";

    public static AnotherPage onPage(WebDriver driver) {
        return PageFactory.initElements(driver, AnotherPage.class);
    }

    public static String getTitle(WebDriver driver) {
        return driver.getTitle();
    }
}
