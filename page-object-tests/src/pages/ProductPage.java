package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    private By backButton = By.className("inventory_details_back_button");

    public static ProductPage visit(WebDriver driver) {
        ProductPage page = new ProductPage(driver);
        driver.get("https://www.saucedemo.com/inventory.html");
        return page;
    }

    public ProductPage(WebDriver driver) { this.driver = driver; }

    public Boolean productStatus() {
        return findTotal(backButton) > 0;
    }
}
