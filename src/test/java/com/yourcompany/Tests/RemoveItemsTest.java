package com.yourcompany.Tests;

import org.junit.Test;
import org.openqa.selenium.By;

public class RemoveItemsTest extends TestBase {

    @Test
    public void removeOneItemFromCart(){
        driver.get("https://www.saucedemo.com/inventory.html");
        driver.findElement(By.className("btn_primary")).click();
        driver.findElement(By.className("btn_primary")).click();
        driver.findElement(By.className("btn_secondary")).click();
    }
}
