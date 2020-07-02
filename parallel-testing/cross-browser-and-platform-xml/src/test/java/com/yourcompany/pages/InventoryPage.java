package com.yourcompany.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage extends BasePO {

    By inventoryContainer = By.className("inventory_container");
    By inventoryList = By.className("inventory_list");
    By inventoryHeader = By.className("header_secondary_container");
    By productTitle = By.className("product_label");

    InventoryPage() {super();}

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Returns the Div containing the item specified (zero-indexed)
     * @param itemNumber
     * @return
     */
    private WebElement getItemNumber(int itemNumber) {
        return driver.findElement(inventoryList).findElement(By.cssSelector(String.format("div.inventory_item:nth-of-type(%d)", itemNumber)));
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
        //return inventoryHeader.isDisplayed();
        return driver.findElement(productTitle).isDisplayed();
    }

    public boolean itemAddedToCart(int itemNumber) {
        return getItemNumber(itemNumber).findElement(By.className("btn_secondary")).isDisplayed();
    }
}
