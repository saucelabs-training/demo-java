package com.yourcompany.Tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveItemsTest extends TestBase {

    @Test
    public void removeOneItemFromCart() {
        getDriver().get("https://www.saucedemo.com/inventory.html");
        getDriver().findElement(By.className("btn_primary")).click();
        getDriver().findElement(By.className("btn_primary")).click();
        getDriver().findElement(By.className("btn_secondary")).click();

        Assert.assertEquals("1", getDriver().findElement(By.className("shopping_cart_badge")).getText());
        
        getDriver().get("http://www.saucedemo.com/cart.html");
        Assert.assertEquals(1, getDriver().findElements(By.className("inventory_item_name")).size());
    }
}