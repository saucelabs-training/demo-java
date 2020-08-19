package android.pages;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwagLabsPage {

    String biometryID = "test-biometry";

    By ProductTitle = By.xpath("//android.widget.TextView[@text='PRODUCTS']");

    public AndroidDriver driver;
    private final int DEFAULT_PIN = 1234;
    private final int INCORRECT_PIN = 4321;

    public SwagLabsPage(AndroidDriver driver) {
        this.driver = driver;

    }

    public void login(boolean successful) {
        try {

            WebElement biometryButton = (WebElement) driver.findElementByAccessibilityId(biometryID);
            biometryButton.click();

            if (successful){
                driver.fingerPrint(DEFAULT_PIN);
            } else {
                driver.fingerPrint(INCORRECT_PIN);
            }


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

    public boolean isRetryBiometryDisplay(){

        try {
            WebElement fingerprintLink = driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Not recognized\")"));
            return fingerprintLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBiometryDisplayed() {
        try {

            WebElement biometryButton = (WebElement) driver.findElementByAccessibilityId(biometryID);
            return biometryButton.isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }
}
