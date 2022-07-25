package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;

public class NewWindowTest extends SauceBaseTest {

    @Test
    public void secondWindow() {
        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.manage().window().setPosition(new Point(100, 400));

        Assertions.assertEquals(2, driver.getWindowHandles().toArray().length);
    }

    @Test
    public void secondTab() {
        driver.switchTo().newWindow(WindowType.TAB);

        Assertions.assertEquals(2, driver.getWindowHandles().toArray().length);
    }
}
