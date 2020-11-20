package Tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class STPTest {

    @Test
    public void safariTechnologyPreview() {
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.setUseTechnologyPreview(true);

        SafariDriver driver = new SafariDriver(safariOptions);

        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertTrue(driver.getTitle().equals("Swag Labs"));

        driver.quit();
    }

}
