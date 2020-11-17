import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class JUnit5W3CChromeTest {
    protected WebDriver driver;
    public Boolean result;

    /**
     * @BeforeEach is a JUnit 5 annotation that defines specific prerequisite test method behaviors.
    In the example below we:
    - Define Environment Variables for Sauce Credentials ("SAUCE_USERNAME" and "SAUCE_ACCESS_KEY")
    - Define Chrome Options such as W3C protocol
    - Define the "sauce:options" capabilities, indicated by the "sauceOpts" MutableCapability object
    - Define the WebDriver capabilities, indicated by the "caps" DesiredCapabilities object
    - Define the service URL for communicating with SauceLabs.com indicated by "sauceURL" string
    - Set the URL to sauceURl
    - Set the driver instance to a RemoteWebDriver
    - Pass "url" and "caps" as parameters of the RemoteWebDriver
    For more information visit the docs: https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/BeforeEach.html
     */

    @BeforeEach
    public void setup(TestInfo testInfo) throws MalformedURLException {
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = testInfo.getDisplayName();

        /** ChomeOptions allows us to set browser-specific behavior such as profile settings, headless capabilities,
         * insecure tls certs, etc.
         * For additional options see: https://chromedriver.chromium.org/capabilities
         * */

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.addArguments("user-data-dir=/path/to/your/custom/profile");

        /** The MutableCapabilities class is now the superclass for handling all option & capabilities implementations,
         * including Selenium Browser Options classes (like ChromeOptions),
         * and is required for Sauce Labs specific configurations
         * */

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("name", methodName);
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accessKey);
        sauceOpts.setCapability("tags", testInfo.getTags());


        /** DesiredCapabilities is no longer the recommended class to use, so to combine both ChromeOptions and
         * Sauce Configuration values into a capabilities instance that can be sent to
         * the RemoteWebDriver, MutableCapabilities should be used here as well.
         * With this approach it makes sense to add the w3c compliant top-level parameters here as well
         * For more information, see: https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options
         */

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("goog:chromeOptions",  chromeOpts);
        caps.setCapability("sauce:options", sauceOpts);
        caps.setCapability("browserName", "chrome");
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("platformName", "windows 10");

        /** Finally, we pass our DesiredCapabilities object 'caps' as a parameter of our RemoteWebDriver instance */
        String sauceUrl = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, caps);
    }
    /**
     * @Tag is a JUnit 5 annotation that defines test method tags that aid in reporting and filtering tests.
    For more information visit the docs: https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/Tag.html

     */
    @Tag("w3c-chrome-tests")
    /**
     * @DisplayName is a JUnit 5 annotation that defines test case name.
    For more information visit the docs: https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/DisplayName.html

     */
    @DisplayName("Junit5W3CChromeTest()")
    /**
     * @Test is a JUnit 5 annotation that defines the actual test case, along with the test execution commands.
    In the example below we:
    - Navigate to our SUT (site under test), 'https://www.saucedemo.com'
    - Store the current page title in a String called 'getTitle'
    - Assert that the page title equals "Swag Labs"
    - Use and If/Else block to determine String match
    For more information visit the docs: https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/Test.html
     */
    @Test
    public void JUnit5w3cChromeTest() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assertions.assertEquals(getTitle, "Swag Labs");
        if (getTitle.equals("Swag Labs")) {
            result = true;
        }else result = false;
    }
    /**
     * @AfterEach is a JUnit 5 annotation that defines any postrequisite test method tasks .
    In the example below we:
    - Use the JavascriptExecutor class to send our test results to Sauce Labs with a "passed" flag
    if the test was successful, or a"failed" flag if the test was unsuccessful.
    - Teardown the RemoteWebDriver session with a 'driver.quit()' command so that the test VM doesn't hang.
    For more information visit the docs: https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/AfterEach.html
     */
    @AfterEach
    public void teardown() {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result ? "passed" : "failed"));
        driver.quit();
    }
}
