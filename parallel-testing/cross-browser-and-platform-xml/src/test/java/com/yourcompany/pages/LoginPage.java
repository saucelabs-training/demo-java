package com.yourcompany.pages;

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

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(className = "btn_action")
    private WebElement submitButton;

    @FindBy(id = "login_credentials")
    private WebElement credentialsInfo;

    @FindBy(className = "svg-inline--fa")
    private WebElement epicSadFace;

    public LoginPage() {
        super();
    }
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);

    }

    public void navigate(){
        driver.get(url);
    }

    public boolean isOnPage() {
        return credentialsInfo.isDisplayed();
    }

    public void login(String username, String password) {
        JavascriptExecutor js = (JavascriptExecutor)driver;

        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);

        // The click doesn't wotk on Safari 13
        //submitButton.click();
        js.executeScript("arguments[0].click()", submitButton);
     //   js.executeScript("arguments[0].dispatchEvent(new Event(\"click\"))", submitButton);
    }

    public boolean epicSadFaceDisplayed() {
        return epicSadFace.isDisplayed();
    }
}
