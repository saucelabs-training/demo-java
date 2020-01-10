package com.yourcompany.Tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test
    public void invalidCredentials() {
        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("bad");
        getDriver().findElement(By.id("password")).sendKeys("bad");
        getDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test
    public void blankCredentials() {
        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("");
        getDriver().findElement(By.id("password")).sendKeys("");
        getDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getDriver().findElements(By.className("error-button")).size() > 0);
    }

    @Test
    public void validCredentials() {
        getDriver().get("https://www.saucedemo.com");
        getDriver().findElement(By.id("user-name")).sendKeys("standard_user");
        getDriver().findElement(By.id("password")).sendKeys("secret_sauce");
        getDriver().findElement(By.className("btn_action")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("inventory"));
    }
}