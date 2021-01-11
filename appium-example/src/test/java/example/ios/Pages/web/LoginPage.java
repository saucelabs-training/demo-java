package example.ios.Pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
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

    public LoginPage() {}

    public boolean isOnPage() {
        return credentialsInfo.isDisplayed();
    }

    public void login(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    public boolean epicSadFaceDisplayed() {
        return epicSadFace.isDisplayed();
    }
}
