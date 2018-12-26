package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class BasePage {
    WebDriver driver;
    private By cartBadge = By.className("shopping_cart_badge");
    private By removeButton = By.className("remove-from-cart-button");
    private By error = By.className("fa-times-circle");

    void click(By locator) {
        waitForElement(locator);
       findBy(locator).click();
    }

    void sendKeys(By locator, String text) {
        waitForElement(locator);
        findBy(locator).sendKeys(text);
    }

    private void waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    WebElement findBy(By locator) {
       return driver.findElement(locator);
    }

    List<WebElement> listAll(By locator) {
        return driver.findElements(locator);
    }

    Integer findTotal(By locator) {
        return listAll(locator).size();
    }

    public void removeRandom() {
        Random random = new Random();
        int randomIndex = random.nextInt(findTotal(removeButton));
        WebElement randomElement = listAll(removeButton).get(randomIndex);
        randomElement.click();
    }
    public void removeAllItems() {
        List<WebElement> removedItems = listAll(removeButton);
        for (int i = 0; i < removedItems.size(); i++) {
            click(removeButton);
        }
        System.out.println(removedItems.size() + " items removed!");
    }
    public String itemCount() {
        return findBy(cartBadge).getText();
    }
    public Integer totalItemsInCart() {
        String number = itemCount();
        int result = Integer.parseInt(number);
        return result;
    }

    public Boolean emptyCart() { return findTotal(cartBadge) == 0; }
    public String getInnerText(By locator) { return findBy(locator).getText();}
    public Boolean hasErrorMessage() {
        return driver.findElements(error).size() > 0;
    }

}
