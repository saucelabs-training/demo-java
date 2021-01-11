package example.ios.Pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage {
    InventoryPage() {}

    @FindBy(className = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "header_secondary_container")
    private WebElement inventoryHeader;

    /**
     * Returns the Div containing the item specified (zero-indexed)
     * @param itemNumber
     * @return
     */
    private WebElement getItemNumber(int itemNumber) {
        return inventoryList.findElement(By.cssSelector(String.format("div.inventory_item:nth-of-type(%d)", itemNumber)));
    }

    public String getItemName(int itemNumber) {
        WebElement itemName = getItemNumber(itemNumber).findElement(By.className("inventory_item_name"));
        return itemName.getText();
    }

    public String getItemDescription(int itemNumber) {
        WebElement itemDesc = getItemNumber(itemNumber).findElement(By.className("inventory_item_desc"));
        return itemDesc.getText();
    }

    public String getItemPrice(int itemNumber) {
        WebElement itemPrice = getItemNumber(itemNumber).findElement(By.className("inventory_item_price"));
        return itemPrice.getText();
    }

    public void addItemToCart(int itemNumber) {
        WebElement addToCart = getItemNumber(itemNumber).findElement(By.className("btn_primary"));
        addToCart.click();
    }

    public boolean isOnPage() {
        return inventoryHeader.isDisplayed();
    }

    public boolean itemAddedToCart(int itemNumber) {
        return getItemNumber(itemNumber).findElement(By.className("btn_secondary")).isDisplayed();
    }
}
