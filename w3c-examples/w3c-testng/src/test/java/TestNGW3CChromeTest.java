import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class TestNGW3CChromeTest {
    protected WebDriver driver;

    /**
     * @BeforeMethod is a TestNG annotation that defines specific prerequisite test method behaviors.
    In the example below we:
    - Define Environment Variables for Sauce Credentials ("SAUCE_USERNAME" and "SAUCE_ACCESS_KEY")
    - Define Chrome Options such as W3C protocol
    - Define the "sauce:options" capabilities, indicated by the "sauceOpts" MutableCapability object
    - Define the WebDriver capabilities, indicated by the "caps" DesiredCapabilities object
    - Define the service URL for communicating with SauceLabs.com indicated by "sauceURL" string
    - Set the URL to sauceURl
    - Set the driver instance to a RemoteWebDriver
    - Pass "url" and "caps" as parameters of the RemoteWebDriver
    For more information visit the docs: http://static.javadoc.io/org.testng/testng/6.9.4/org/testng/annotations/BeforeMethod.html
     */
    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = method.getName();

        /** The MutableCapabilities class is now the superclass for handling all option & capabilities implementations,
         * including Selenium Browser Options classes (like ChromeOptions),
         * and is required for Sauce Labs specific configurations
         * For available options, see: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options
         */

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("name", methodName);
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accessKey);
        sauceOpts.setCapability("tags", "w3c-chrome-tests");

        /** DesiredCapabilities is being deprecated in favor of using Browser Options classes for everything.
         * Browser Options support both standard w3c values (https://w3c.github.io/webdriver/#capabilities)
         * as well as browser-specific values (https://chromedriver.chromium.org/capabilities)
         * including behavior such as profile settings, mobile emulation, headless capabilities, insecure tls certs, etc.
         *
         * Sauce Configuration values can be addded directly to the browser options class
         */

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.addArguments("user-data-dir=/path/to/your/custom/profile");

        chromeOpts.setCapability("browserName", "googlechrome");
        chromeOpts.setCapability("browserVersion", "latest");
        chromeOpts.setCapability("platformName", "windows 10");

        chromeOpts.setCapability("sauce:options", sauceOpts);

        /**
         * Finally, we pass our Browser Options instance as a parameter of our RemoteWebDriver constructor
        */
        String sauceUrl = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, chromeOpts);
    }

    /**
     * @Test is a TestNG annotation that defines the actual test case, along with the test execution commands.
    In the example below we:
    - Navigate to our SUT (site under test), 'https://www.saucedemo.com'
    - Store the current page title in a String called 'getTitle'
    - Assert that the page title equals "Swag Labs"
    For more information visit the docs: http://static.javadoc.io/org.testng/testng/6.9.4/org/testng/annotations/Test.html
     */
    @Test
    public void TestNGw3cChromeTest() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assert.assertEquals(getTitle, "Swag Labs");
    }
    /**
     * @AfterMethod is a TestNG annotation that defines any postrequisite test method tasks .
    In the example below we:
    - Pass the ITestResult class results to a parameter called 'result'
    - Use the JavascriptExecutor class to send our test 'result' to Sauce Labs with a "passed" flag
    if the test was successful, or a "failed" flag if the test was unsuccessful.
    - Teardown the RemoteWebDriver session with a 'driver.quit()' command so that the test VM doesn't hang.
    For more information visit the docs: http://static.javadoc.io/org.testng/testng/6.9.4/org/testng/annotations/AfterMethod.html
     */
    @AfterMethod
    public void teardown(ITestResult result) {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
}
