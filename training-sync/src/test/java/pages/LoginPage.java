package pages;

import com.saucelabs.salsaverde.elements.Element;
import com.saucelabs.salsaverde.pages.OnPage;
import com.saucelabs.salsaverde.pages.PageObject;
import data.UserData;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

@OnPage(url="https://www.saucedemo.com/", title="Swag Labs", elements={"usernameField", "passwordField"})
public class LoginPage extends PageObject {
    private final Element usernameField = browser.element(By.id("user-name"));
    private final Element passwordField = browser.element(By.id("password"));
    private final Element submitButton = browser.element(By.className("btn_action"));
    private final Element error = browser.element(By.cssSelector("[data-test=error]"));

    // Opinionated methods allow for synchronization where it matters
    @SneakyThrows
    public void loginSuccessfully(UserData userData) {
        login(userData);

        try {
            synchWait().until((page) -> !isOnPage());
        } catch (TimeoutException e) {
            throw new TimeoutException("login was unsuccessful; found error message: " + error.getText());
        }
    }

    @SneakyThrows
    public void loginFailure(UserData userData) {
        login(userData);

        try {
            synchWait().until((page) -> error.doesExist());
        } catch (TimeoutException e) {
            throw new TimeoutException("expected unsuccessful login to show errors, but none were found");
        }
    }

    public void login(UserData user) {
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        submitButton.click();
    }

    public WebDriverWait synchWait() {
        return new WebDriverWait(browser.getDriver(), 30);
    }
}
