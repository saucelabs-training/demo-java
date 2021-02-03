package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPagePart() {
        return "";
    }

    public void login(String userName) {
        //Create an instance of a Selenium explicit wait
        // so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //wait for the user name field to be visible and store that element into a variable
        By userNameFieldLocator = By.cssSelector("#user-name");
        WebElement userNameField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator));
        userNameField.sendKeys(userName);

        driver.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("#login-button")).click();
    }


}
