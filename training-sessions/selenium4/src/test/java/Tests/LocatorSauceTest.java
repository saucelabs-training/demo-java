package Tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;

public class LocatorSauceTest extends SauceTestBase {

    @Test
    public void relativeLocatorsBad() {
        driver.navigate().to("https://www.saucedemo.com/inventory");

        List<WebElement> inventoryItems = driver.findElements(By.className("inventory_item"));

        driver.findElement(withTagName("button")
                .toRightOf(inventoryItems.get(2))).click();

        driver.findElement(withTagName("button")
                .below(inventoryItems.get(1))).click();
    }

    @Test
    public void relativeLocatorsGood() throws InterruptedException {
        // https://github.com/diemol/selenium-4-demo
        driver.manage().window().maximize();
        driver.get("https://www.diemol.com/selenium-4-demo/relative-locators-demo.html");

        WebElement element = driver.findElement(withTagName("li")
                .toLeftOf(By.id("boston"))
                .below(By.id("warsaw")));
        blur(driver, element);
        unblur(driver, element);
    }

    public void blur(RemoteWebDriver driver, WebElement webElement) throws InterruptedException {
        driver.executeScript("arguments[0].style.filter='blur(8px)'", webElement);
        // Thread.sleep only meant for demo purposes!
        Thread.sleep(5000);
    }

    public void unblur(RemoteWebDriver driver, WebElement webElement) throws InterruptedException {
        driver.executeScript("arguments[0].style.filter='blur(0px)'", webElement);
        // Thread.sleep only meant for demo purposes!
        Thread.sleep(5000);
    }
}
