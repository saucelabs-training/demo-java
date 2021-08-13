package com.saucedemo.selenium.testng.demo;

import com.saucelabs.saucebindings.testng.SauceBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SauceBindingsTestngTest extends SauceBaseTest {
    @Test
    public void correctTitle() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }
}
