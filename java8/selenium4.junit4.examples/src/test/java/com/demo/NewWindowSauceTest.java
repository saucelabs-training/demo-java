package com.demo;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;

public class NewWindowSauceTest extends SauceTestBase {

    @Test
    public void secondWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.manage().window().setPosition(new Point(100, 400));

        Assert.assertEquals(2, driver.getWindowHandles().toArray().length);
    }

    @Test
    public void secondTab() {
        driver.switchTo().newWindow(WindowType.TAB);

        Assert.assertEquals(2, driver.getWindowHandles().toArray().length);
    }
}
