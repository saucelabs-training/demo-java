package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import data.User;

public class LogInPage extends BasePage {
    private By userField = By.cssSelector("[placeholder = 'Username']");
    private By passwordField = By.cssSelector("[placeholder = 'Password']");
    private By loginButton = By.className("login-button");

    public static LogInPage visit(WebDriver driver) {
        LogInPage page = new LogInPage(driver);
        driver.get("https://www.saucedemo.com");
        return page;
    }

    public LogInPage(WebDriver driver) {
        this.driver = driver;
    }
    public void signIn(User data) {
        fillForm(data);
    }
    public void signInUnsuccessfully(User data) {
        fillForm(data);
    }
    private void fillForm(User data) {
        sendKeys(userField, data.getUsername());
        sendKeys(passwordField, data.getPassword());
        click(loginButton);
    }
}
