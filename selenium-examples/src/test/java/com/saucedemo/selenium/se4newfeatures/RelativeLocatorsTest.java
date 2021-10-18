package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class RelativeLocatorsTest extends SauceBaseTest {

    @Test
    public void relativeLocators() {
        driver.get("https://www.diemol.com/selenium-4-demo/relative-locators-demo.html");

        WebElement element = driver.findElement(with(By.tagName("li"))
                .toLeftOf(By.id("berlin"))
                .below(By.id("warsaw")));

        Assertions.assertEquals("london", element.getAttribute("id"));

        driver.executeScript("arguments[0].style.filter='blur(8px)'", element);
    }
}
