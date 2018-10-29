import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.Assert;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class InstantJavaTestNGTest {

    private WebDriver driver;

    @Test
    public void shouldOpenSafari(Method method) throws MalformedURLException {
        /** Here we are reading environment variables from your local machine and storing these
         * values in the variables below. Doing this is a best practice.
         *
         * Not sure how to use env variables, follow this -
         * https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials
         */
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");


        /**
         * In this section, we will configure our test to run on some specific
         * browser/os combination in Sauce Labs
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        //set your user name and access key to run tests in Sauce
        capabilities.setCapability("username", sauceUserName);
        //set your sauce labs access key
        capabilities.setCapability("accessKey", sauceAccessKey);
        //set browser to Safari
        capabilities.setCapability("browserName", "Safari");
        //set operating system to macOS version 10.13
        capabilities.setCapability("platform", "macOS 10.13");
        //set the browser version to 11.1
        capabilities.setCapability("version", "11.1");
        //set your test case name so that it shows up in Sauce Labs
        capabilities.setCapability("name", method.getName());

        //create a new Remote driver that will allow your test to send
        //commands to the Sauce Labs grid so that Sauce can execute your tests
        driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);

        //navigate to the url of the Sauce Labs Sample app
        driver.navigate().to("https://www.saucedemo.com");
        //Create an instance of a Selenium explicit wait so that we can dynamically wait for an element
        WebDriverWait wait = new WebDriverWait(driver, 5);
        //wait for the user name field to be visible and store that element into a variable
        By userNameFieldLocator = By.cssSelector("[type='text']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameFieldLocator));
        //type the user name string into the user name field
        driver.findElement(userNameFieldLocator).sendKeys("standard_user");
        //type the password into the password field
        driver.findElement(By.cssSelector("[type='password']")).sendKeys("secret_sauce");
        //hit Login button
        driver.findElement(By.cssSelector("[type='submit']")).click();

        //Synchronize on the next page and make sure it loads

        By inventoryPageLocator = By.id("inventory_container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryPageLocator));
        //Assert that the inventory page displayed appropriately
        Assert.assertTrue(driver.findElement(inventoryPageLocator).isDisplayed());
    }


    /**
     *Below we are performing 2 critical actions. Quitting the driver and passing
     * the test result to Sauce Labs user interface.
     */
    @AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result)
    {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
}
