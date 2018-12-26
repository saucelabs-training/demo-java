package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import data.User;

public class ShippingPage extends BasePage {
    private By firstNamefield = By.cssSelector("[placeholder = 'First Name']");
    private By lastNameField = By.cssSelector("[placeholder = 'Last Name']");
    private By zipField = By.cssSelector("[placeholder = 'Zip/Postal Code']");
    private By continueButton = By.cssSelector("[value = 'CONTINUE']");
    private By emptyFirstName = By.cssSelector("[placeholder='First Name'][value='']");
    private By emptyLastName = By.cssSelector("[placeholder='Last Name'][value='']");
    private By emptyZipCode = By.cssSelector("[placeholder = 'Zip/Postal Code'][value='']");

    public static ShippingPage visit(WebDriver driver) {
        ShippingPage page = new ShippingPage(driver);
        driver.get("https://www.saucedemo.com/checkout-step-one.html");
        return page;
    }

    public ShippingPage(WebDriver driver) {
        this.driver = driver;
    }

    public Boolean readyToShip() {
        return findTotal(firstNamefield) > 0;
    }

    public void successfulShippingInfo(User data) {
        fillForm(data);
    }

    public void unsuccessfulShippingInfo(User data) {
        fillForm(data);
    }

    private void fillForm(User data) {
        sendKeys(firstNamefield, data.getFirstname());
        sendKeys(lastNameField, data.getLastname());
        sendKeys(zipField, data.getZipcode());
        click(continueButton);
    }

    public Boolean nullFirstName() {return findTotal(emptyFirstName) == 1; }
    public Boolean nullLastName() {return findTotal(emptyLastName) == 1; }
    public Boolean nullZipCode() {return findTotal(emptyZipCode) == 1;}
}