package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;

/**
 * Created by mehmetgerceker on 12/7/15.
 */
public class PageBase {

    WebDriver driver;

    protected static void setTextAreaInputValue(WebElement textArea, String value)
            throws InvalidElementStateException {
        setTextElementText(textArea, value);
    }

    protected static void clickButton(WebElement button) throws InvalidElementStateException {
        button.click();
    }

    protected static void clickLink(WebElement link) throws InvalidElementStateException {
        link.click();
    }

    private static void setTextElementText(WebElement textElement, String value)
            throws InvalidElementStateException {
        textElement.sendKeys(value);
    }
}
