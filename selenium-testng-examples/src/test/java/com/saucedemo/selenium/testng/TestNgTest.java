package com.saucedemo.selenium.testng;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestNgTest {
    protected WebDriver driver;
    private SauceSession session;

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
    public void setup(Method method) {
        SauceOptions options = new SauceOptions();
        options.setName(method.getName());

        session = new SauceSession(options);
        driver = session.start();
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
    public void chromeWorks() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
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
        session.stop(result.isSuccess());
    }
}
