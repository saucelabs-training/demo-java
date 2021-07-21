package com.saucedemo.selenium.junit4;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

public class JUnit4Test {
    protected WebDriver driver;
    private SauceSession session;
    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    /**
     * @Before is a JUnit4 annotation that defines specific prerequisite test method behaviors.
     * In the example below we:
     * - Define Environment Variables for Sauce Credentials ("SAUCE_USERNAME" and "SAUCE_ACCESS_KEY")
     * - Define Chrome Options such as W3C protocol
     * - Define the "sauce:options" capabilities, indicated by the "sauceOpts" MutableCapability object
     * - Define the WebDriver capabilities, indicated by the "caps" DesiredCapabilities object
     * - Define the service URL for communicating with SauceLabs.com indicated by "sauceURL" string
     * - Set the URL to sauceURl
     * - Set the driver instance to a RemoteWebDriver
     * - Pass "url" and "caps" as parameters of the RemoteWebDriver
     * For more information visit the docs: https://junit.org/junit4/javadoc/4.12/org/junit/Before.html
     */
    @Before
    public void setup() {
        SauceOptions options = new SauceOptions();
        options.setName(testName.getMethodName());

        session = new SauceSession(options);
        driver = session.start();
    }

    /**
     * @Test is a JUnit4 annotation that defines the actual test case, along with the test execution commands.
     * In the example below we:
     * - Navigate to our SUT (site under test), 'https://www.saucedemo.com'
     * - Store the current page title in a String called 'getTitle'
     * - Assert that the page title equals "Swag Labs"
     * For more information visit the docs: https://junit.org/junit4/javadoc/4.12/org/junit/Test.html
     */
    @Test
    public void chromeWorks() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }

    /**
     * @After is a JUnit4 annotation that defines any postrequisite test method tasks .
     * In the example below we:
     * - Pass the ITestResult class results to a parameter called 'result'
     * - Use the JavascriptExecutor class to send our test 'result' to Sauce Labs with a "passed" flag
     * if the test was successful, or a "failed" flag if the test was unsuccessful.
     * - Teardown the RemoteWebDriver session with a 'driver.quit()' command so that the test VM doesn't hang.
     * For more information visit the docs: https://junit.org/junit4/javadoc/4.12/org/junit/After.html
     */
    @After
    public void teardown() {
        session.stop(true);
    }
}
