package android.pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class SwagLabsPage {

    String usernameID = "test-Username";
    String passwordID = "test-Password";
    String submitButtonID = "test-LOGIN";

    By ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");

    By testMenu = By.xpath("//android.view.ViewGroup[@content-desc='test-Menu']");
    By testMenuItemQRCode = By.xpath("//android.view.ViewGroup[@content-desc='test-QR CODE SCANNER']");
    By testMenuItemWebView = By.xpath("//android.view.ViewGroup[@content-desc='test-WEBVIEW']");

    public AndroidDriver driver;

    public SwagLabsPage(AndroidDriver driver) {
        this.driver = driver;

    }

    public void login(String user, String pass) {
        try {
            driver.context("NATIVE_APP");

            WebDriverWait wait = new WebDriverWait(driver, 10);
            final WebElement usernameEdit = wait.until(ExpectedConditions.visibilityOfElementLocated(new MobileBy.ByAccessibilityId(usernameID)));

            usernameEdit.click();
            usernameEdit.sendKeys(user);


            WebElement passwordEdit = (WebElement) driver.findElementByAccessibilityId(passwordID);
            passwordEdit.click();
            passwordEdit.sendKeys(pass);

            WebElement submitButton = (WebElement) driver.findElementByAccessibilityId(submitButtonID);
            submitButton.click();

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    public boolean isOnProductsPage() {

        //Create an instance of a Selenium explicit wait so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);

        //wait for the product field to be visible and store that element into a variable
        try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ProductTitle));
        } catch (TimeoutException e){
            return false;
        }
        return true;
    }

    public void clickMenu() {
        driver.findElement(testMenu).click();
    }
    public void selecMenuQRCodeScanner() {
        WebDriverWait wait = new WebDriverWait(driver, 5);

        WebElement QCCodeMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(testMenuItemQRCode));
        QCCodeMenu.click();
    }
    public void selecMenuWebView() {
        driver.findElement(testMenuItemWebView).click();
    }

}
