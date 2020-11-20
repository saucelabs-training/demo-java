package Tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class MSEdgeTest {

    @Test
    public void edgeExecution() {
        EdgeOptions edgeOptions = new EdgeOptions();
        EdgeDriver driver = new EdgeDriver(edgeOptions);

        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertTrue(driver.getTitle().equals("Swag Labs"));

        driver.quit();
    }
}
