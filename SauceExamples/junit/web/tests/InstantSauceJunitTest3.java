package junit.web.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstantSauceJunitTest3 {
    private WebDriver driver;

    @Test
    @DisplayName("shouldOpenChrome()")
    public void shouldOpenChrome(TestInfo testInfo) throws MalformedURLException {
        /** Here we set environment variables from your local machine, or IntelliJ run configuration,
         *  and store these values in the variables below. Doing this is a best practice in terms of test execution
         *  and security. If you're not sure how to use env variables, refer to this guide -
         * https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials
         * or check junit5-README.md */
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        /**
         * In this exercise use the Platform Configurator, located here:
         * https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
         * in order to replace the following DesiredCapabilities: browserName, platform, and version
         * For example, I chose to use Windows 10 with Chrom version 59.
         * Note: If you use Chrome version 61+ you must use the sauce:options capability.
         * More info here: https://wiki.saucelabs.com/display/DOCS/Selenium+W3C+Capabilities+Support+-+Beta
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("version", "59.0");
        capabilities.setCapability("name", testInfo.getDisplayName());

        /**
         * Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */

        driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);
        driver.navigate().to("https://www.saucedemo.com");

        /**
         * Synchronize on the next page and make sure it loads.
         * In this section, we confirm the test ran correctly by checking the page title.
         * Change the value 'Swag Labs' of pageTitle to the value in your application's html page */

        /**
         * Change the value 'Swag Labs' of pageTitle to the value in your application's html page */

        String actualPageTitle = driver.getTitle();
        String pageTitle = "Swag Labs";
        assertEquals(pageTitle, actualPageTitle);

        /** Not sure how to grab the page title? Use a browser developer tools; for example in Chrome it's:
         *  View > Developer > Developer Tools. Then you can view the page title in the right column */

        /**
         * * Below we are performing 2 critical actions. Quitting the driver and passing
         * the test result to Sauce Labs user interface.
         * Again, make sure you change the conditional in the if statement
         */
        if (pageTitle == "Swag Labs") {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        } else {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
        }
        driver.quit();
    }
}