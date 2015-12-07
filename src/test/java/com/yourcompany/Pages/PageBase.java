package com.yourcompany.Pages;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
/**
 * Created by mehmetgerceker on 12/7/15.
 */
public class PageBase {

    protected static void setCheckCheckBoxState(WebElement checkBox, boolean checked)
            throws InvalidElementStateException {
        if (checkBox.getAttribute("type") != "checkbox" || checkBox.getTagName() != "input"){
            throw new InvalidElementStateException("This web element is not a checkbox!");
        }
        //we may wanna check if it is displayed and enabled, when performing actions.
        if ( checkBox.isDisplayed() && checkBox.isEnabled()){
            if (checkBox.isSelected() != checked) {
                checkBox.click();
            }
        } else {
            throw new InvalidElementStateException("Checkbox by "
                    + checkBox.getAttribute("id")
                    + " is disabled!");
        }

    }

    protected static void setTextInputValue(WebElement textInput, String value)
            throws InvalidElementStateException {
        setTextElementText(textInput, "text", value);

    }

    protected static void setTextAreaInputValue(WebElement textArea, String value)
            throws InvalidElementStateException{
        setTextElementText(textArea, "textarea", value);
    }

    protected static void clickButton(WebElement button) throws InvalidElementStateException{
        if (button.getAttribute("type") != "submit" || button.getTagName() != "input"){
            throw new InvalidElementStateException("This web element is not a button input!");
        }
        //we may wanna check if it is displayed and enabled, when performing actions.
        if (button.isDisplayed() && button.isEnabled()){
            button.click();
        } else {
            throw new InvalidElementStateException("Button by "
                    + button.getAttribute("id")
                    + " is disabled or not displayed!");
        }
    }

    private static void setTextElementText(WebElement textElement, String textInputType, String value)
            throws InvalidElementStateException {
        if (textElement.getAttribute("type") != value || textElement.getTagName() != "input"){
            throw new InvalidElementStateException("This web element is not a text input!");
        }
        //we may wanna check if it is displayed and enabled, when performing actions.
        if (textElement.isDisplayed() && textElement.isEnabled()){
            textElement.click();
            textElement.clear();
            textElement.sendKeys(value);
        } else {
            throw new InvalidElementStateException("Text input by "
                    + textElement.getAttribute("id")
                    + " is disabled or not displayed!");
        }
    }
}
