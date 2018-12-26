package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class InventoryPage extends BasePage {
    private By backpackBtn = By.cssSelector("#inventory_container > div > div:nth-child(1) > div.pricebar > button");
    private By jacketBtn =  By.cssSelector("#inventory_container > div > div:nth-child(4) > div.pricebar > button");
    private By addToCartButton = By.className("add-to-cart-button");
    private By cartLink =  By.className("shopping_cart_link");
    private By checkoutLink = By.className("cart_checkout_link");
    private By itemName = By.className("iventory_item_name");
    private By itemLink = By.cssSelector("a[id^='item_'][id$='_title_link']");



    private By sauceBot = By.className("peek");

    public static InventoryPage visit(WebDriver driver) {
        InventoryPage page = new InventoryPage(driver);
        driver.get("https://www.saucedemo.com/inventory.html");
        return page;
    }

    public InventoryPage(WebDriver driver) { this.driver = driver; }

    public void addOneItem() {

        click(backpackBtn);
    }

    public void addTwoItems() {
        click(backpackBtn);
        click(jacketBtn);
    }

    public void addAllItems() {
        List<WebElement> addedItems = listAll(addToCartButton);
        for (int i = 0; i <  addedItems.size(); i++) {
            click(addToCartButton);
        }
        System.out.println(addedItems.size() + " items added!");
    }
    public void proceedToCheckout() {
        click(cartLink);
        click(checkoutLink);
    }

    public void clickRandomLink() {
        Random random = new Random();
        int randomIndex = random.nextInt(findTotal(itemLink));
        WebElement randomLink = listAll(itemLink).get(randomIndex);
        randomLink.click();
    }

    public void addRandomItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(findTotal(addToCartButton));
        WebElement randomItem = listAll(addToCartButton).get(randomIndex);
        randomItem.click();
    }

    public void addRandomItems() {
        int max = 6;
        int min = 1;
        int range = max - min +1;
        int rand = (int) (Math.random() * range) + min;
        for (int i =0; i < rand; i++) {
            addRandomItem();
        }
    }

    public String getItemName() {
        return findBy(itemName).getText();
    }

    public Boolean isSignedIn() {
        return findTotal(sauceBot) > 0;
    }
    /*public void addTwoBackPacks() {
        for (int i =0; i < 3; i++) {
            click(backpackBtn);
        }
    }

    public void addTwoJackets() {
        for (int i = 0; i < 3; i++) {
            click(jacketBtn);
        }
    }*/
}
