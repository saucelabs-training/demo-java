package ios.pages;

import helpers.utils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class SwagLabsPage {

    By usernameID = By.id("test-Username");
    By passwordID = By.id("test-Password");
    By submitButton = By.id("test-LOGIN");
    String ProductTitleSelector = "type==\"XCUIElementTypeStaticText\" && name==\"PRODUCTS\"";
    By testMenu = By.name("test-Menu");
    By testMenuItemWebView = By.name("test-WEBVIEW");
    By testMenuItemQRCode = By.name("test-QR CODE SCANNER");
    By acceptCameraButton = By.name("OK");

    public IOSDriver driver;

    public SwagLabsPage(IOSDriver driver) {
        this.driver = (IOSDriver) driver;

    }

    public void login(String user, String pass) {
        try {

            WebElement usernameEdit = driver.findElement(usernameID);
            usernameEdit.click();
            usernameEdit.sendKeys(user);

            WebElement passwordEdit = driver.findElement(passwordID);
            passwordEdit.click();
            passwordEdit.sendKeys(pass);

            driver.findElement(submitButton).click();

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    public boolean isOnProductsPage() {
        return driver.findElementByIosNsPredicate(ProductTitleSelector).isDisplayed();
    }

    public void clickMenu() {
        driver.findElement(testMenu).click();
    }

    public void selecMenuQRCodeScanner() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement QCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(testMenuItemQRCode));
        QCCodeMenu.click();
    }

    public void acceptCameraAccess(){
        // In Sauce Labs (Legacy) RDC Android permissions are enabled by default.
        /// iOS needs to be done with automation
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            WebElement acceptCameraBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(acceptCameraButton));
            acceptCameraBtn.click();
        } catch (Exception e) {
            // Do nothing, the alert was not shown
        }
    }

}
