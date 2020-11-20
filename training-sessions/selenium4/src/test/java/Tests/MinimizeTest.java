package Tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class MinimizeTest {

    @Test
    public void minimizeWindow() {
        EdgeOptions edgeOptions = new EdgeOptions();
        EdgeDriver driver = new EdgeDriver(edgeOptions);

        driver.manage().window().minimize();

        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertTrue(driver.getTitle().equals("Swag Labs"));

        driver.quit();
    }
}
