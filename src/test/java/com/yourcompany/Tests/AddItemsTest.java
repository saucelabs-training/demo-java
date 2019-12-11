package com.yourcompany.Tests;

import org.junit.Test;
import org.openqa.selenium.By;

public class AddItemsTest extends TestBase {

    @Test
    public void addOneItemtoCart(){
        driver.get("https://www.saucedemo.com/inventory.html");
        driver.findElement(By.className("btn_primary")).click();
    }

    @Test
    public void addTwoItemsToCart(){
        driver.get("https://www.saucedemo.com/inventory.html");
        driver.findElement(By.className("btn_primary")).click();
        driver.findElement(By.className("btn_primary")).click();
    }
}
