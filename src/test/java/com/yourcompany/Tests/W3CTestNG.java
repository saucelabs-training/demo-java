package com.yourcompany.Tests;

import com.yourcompany.Pages.GuineaPigPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class W3CTestNG {

    private WebDriver driver;
    private MutableCapabilities options;

    /**
     * Parameters used here represent options for W3C compliant
     * browsers (Chrome is currently not compliant)
     * @return
     */
    @DataProvider(parallel = true)
    public static Object[][] data() {
     return new Object[][] {
             //{new ChromeOptions()},
             {new FirefoxOptions(), "latest", "Windows 10"},
             {new FirefoxOptions(), "58.0", "OS X 10.12"},
             {new EdgeOptions(), "latest", "Windows 10"},
             {new SafariOptions(), "latest", "OS X 10.12"},
             {new InternetExplorerOptions(), "latest", "Windows 7"},
        };
    }

    /**
     * Utility function that makes use of W3C Options classes.
     *
     * @param options
     * @param browserVersion
     * @param platformName
     * @throws MalformedURLException
     */
    public void createDriverOptions(MutableCapabilities options, String browserVersion, String platformName) throws MalformedURLException{
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        options.setCapability("browserVersion", browserVersion);
        options.setCapability("platformName", platformName);

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("seleniumVersion", "3.11.0");
        sauceOptions.setCapability("name", "W3CTestNG");
        sauceOptions.setCapability("build", "W3C");

        options.setCapability("sauce:options", sauceOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + "@ondemand.saucelabs.com/wd/hub"), options);
    }

    @AfterMethod
    public void teardown(ITestResult result){
        driver.quit();
    }

    @Test(dataProvider = "data")
    public void simpleCase(MutableCapabilities options, String browserVersion, String platformName) throws MalformedURLException {
        this.createDriverOptions(options, browserVersion, platformName);

        GuineaPigPage page = GuineaPigPage.visitPage(driver);
        String title = page.driver.getTitle();

        Assert.assertTrue(title.contains("Sauce"));
    }
}
