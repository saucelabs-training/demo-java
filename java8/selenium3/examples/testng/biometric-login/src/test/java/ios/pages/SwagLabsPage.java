package ios.pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SwagLabsPage {

    By biometryButton = By.id("test-biometry");
    String ProductTitleSelector = "type==\"XCUIElementTypeStaticText\" && name==\"PRODUCTS\"";


    public IOSDriver driver;

    public SwagLabsPage(IOSDriver driver) {
        this.driver = (IOSDriver) driver;

    }

    public boolean isBiometryDisplayed(){
        try {
            return driver.findElement(biometryButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }
    public void login(boolean successful) {
        try {

            WebElement biometryBtn = driver.findElement(biometryButton);
            biometryBtn.click();

            driver.performTouchID(successful);

        } catch (Exception e) {
            System.out.println("*** Problem to login: " + e.getMessage());
        }
    }

    public boolean isOnProductsPage() {

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        boolean isDisplay =  driver.findElementByIosNsPredicate(ProductTitleSelector).isDisplayed();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        return isDisplay;
    }


    public boolean isRetryBiometryDisplay(boolean rdc){
        String elementText;

        try {
            if (rdc) {
                elementText = "Cancel";
            } else {
                elementText = "Try Again";
            }

            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementText)));

            } catch (TimeoutException e){
                return false;
            }
            return true;
    }

}
