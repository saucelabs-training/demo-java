import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    //TODO how will you handle the isLoaded method that's in a few pages
    public boolean isLoaded() {
        //TODO what are you going to do with the element locators if you keep reusing them in different methods?
        WebElement logo = driver.findElement(By.className("app_logo"));
        return pageWait.until(ExpectedConditions.visibilityOf(logo)).isDisplayed();
    }
}
