package Tests;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class LocatorSauceTest extends SauceBaseTest {

    @Test
    public void relativeLocatorsGood() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("https://www.diemol.com/selenium-4-demo/relative-locators-demo.html");

        WebElement element = driver.findElement(with(By.tagName("li"))
                .toLeftOf(By.id("berlin"))
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
