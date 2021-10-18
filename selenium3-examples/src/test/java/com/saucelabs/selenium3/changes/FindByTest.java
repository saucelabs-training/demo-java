package com.saucelabs.selenium3.changes;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Test;
public class FindByTest extends SauceBaseTest {

    @Test
    public void findElementBy() {
        driver.navigate().to("https://www.saucedemo.com");

        driver.findElementById("user-name");
        driver.findElementByCssSelector("#password");
        driver.findElementByClassName("btn_action");
    }
}
