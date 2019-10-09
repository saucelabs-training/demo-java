import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SampleHeadlessSauceTest {
    private WebDriver driver;

    @Test
    public void main() throws MalformedURLException {
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        String URL = "https://ondemand.us-east-1.saucelabs.com/wd/hub";

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", sauceUserName);
        sauceOptions.setCapability("accessKey", sauceAccessKey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", "headless-chrome-test-java");
        sauceOptions.setCapability("build", "Sample Headless Tests");

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("goog:chromeOptions", chromeOpts);
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("platformName", "Linux");
        caps.setCapability("sauce:options", sauceOptions);

        driver = new RemoteWebDriver(new URL(URL), caps);

        /* Goes to Sauce Lab's demo page and prints title */

        driver.get("https://www.saucedemo.com");
        System.out.println("title of page is: " + driver.getTitle());
        Assert.assertEquals(driver.getTitle(), "Swag Labs" );
    }
    /* Sends results to SauceLabs.com */
    @AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
}
