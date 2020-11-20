package Tests;

import org.junit.Assert;
import org.junit.Test;

public class SauceDemoTest extends TestBase {
    @Test
    public void SauceDemo() {
        driver.get("https://www.saucedemo.com/");

        Assert.assertTrue(driver.getTitle().equals("Swag Labs"));

        driver.quit();
    }
}
