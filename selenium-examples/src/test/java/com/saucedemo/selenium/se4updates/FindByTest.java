package com.saucedemo.selenium.se4updates;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
public class FindByTest extends SauceBaseTest {

    @Test
    public void findElement() {
        driver.navigate().to("https://www.saucedemo.com");

        // These are no longer available at all in Selenium 4:
        //  driver.findElementById("user-name");
        //  driver.findElementByCssSelector("#password");
        //  driver.findElementByClassName("btn_action");

        driver.findElement(By.cssSelector("#user-name"));
        driver.findElement(By.cssSelector("#password"));
        driver.findElement(By.cssSelector(".btn_action"));
    }
}
