package com.yourcompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePO {
    private String url = "https://www.saucedemo.com/";

    public String getUrl() {
        return url;
    }

    By usernameInput = By.id("user-name");
    By passwordInput = By.id("password");
    By submitButton = By.className("btn_action");
    By credentialsInfo = By.id("login_credentials");
    By epicSadFace = By.className("svg-inline--fa");

    public LoginPage() {
        super();
    }
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigate(){
        driver.get(url);
    }

    public boolean isOnPage() {
        return driver.findElement(credentialsInfo).isDisplayed();
    }

    public void login(String username, String password) {
        JavascriptExecutor js = (JavascriptExecutor)driver;

        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);

        // The click doesn't wotk on Safari 13
        //driver.findElement(submitButton).click();
        js.executeScript("arguments[0].click()", driver.findElement(submitButton));
        // This is an example of click by using dispatch event
     //   js.executeScript("arguments[0].dispatchEvent(new Event(\"click\"))", driver.findElement(submitButton));
    }

    public boolean epicSadFaceDisplayed() {
        return driver.findElement(epicSadFace).isDisplayed();
    }
}
