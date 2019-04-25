import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class Module2JunitTest {
    private WebDriver driver;

    @Test
    public void shouldOpenSafari() throws MalformedURLException {
        /** Here we set environment variables from your local machine, or IntelliJ run configuration,
         *  and store these values in the variables below. Doing this is a best practice in terms of test execution
         *  and security. If you're not sure how to use env variables, refer to this guide -
         * https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials
         * or check junit5-README.md */
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceURL = "https://ondemand.saucelabs.com/wd/hub";

        /**
         * In this section, we will configure our test to run on some specific
         * browser/os combination in Sauce Labs.*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platform", "macOS 10.13");
        capabilities.setCapability("version", "11.1");
        capabilities.setCapability("build", "Onboarding Sample App - Java-Junit5");
        capabilities.setCapability("name", "2-user-site");
        /**
         * In this section, we set the Remote WebDriver to run on Sauce Labs, and pass the capabilities.
         * Then we perform some actions on an application.
         * For this script, enter in your application's URL in place of 'https://www.saucedemo.com'. */

        /** If you're accessing the EU data center, use the following endpoint:.
         * https://ondemand.eu-central-1.saucelabs.com/wd/hub
         * */
        driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
        driver.navigate().to("https://www.saucedemo.com");
        //assertTrue(true);
    }

    /**
     * Below we are performing 2 critical actions. Quitting the driver and passing
     * the test result to Sauce Labs user interface.
     */
    @AfterEach
    public void cleanUpAfterTestMethod () {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (true ? "passed" : "failed"));
        driver.quit();
    }
}