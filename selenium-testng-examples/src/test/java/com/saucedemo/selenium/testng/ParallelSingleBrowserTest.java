package com.saucedemo.selenium.testng;

import com.saucelabs.saucebindings.testng.SauceBaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ParallelSingleBrowserTest extends SauceBaseTest {
    @Test
    public void testCase1() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase2() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase3() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase4() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase5() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase6() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase7() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase8() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase9() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }

    @Test
    public void testCase10() {
        getDriver().navigate().to("https://www.saucedemo.com");
        Assert.assertEquals("Swag Labs", getDriver().getTitle());
    }
}
