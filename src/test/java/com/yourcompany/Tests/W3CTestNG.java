package com.yourcompany.Tests;

import com.yourcompany.Pages.GuineaPigPage;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class W3CTestNG {

    private WebDriver driver;

    /**
     * Parameters used here represent options for W3C compliant
     * browsers (Chrome is currently not compliant)
     * @return
     */
    @DataProvider
    public static Object[][]  data() {
     return new Object[][] {
                //{new ChromeOptions()},
                {new FirefoxOptions()},
                {new EdgeOptions()},
                {new SafariOptions()},
                {new InternetExplorerOptions()}
        };
    }

    @AfterMethod
    public void teardown(ITestResult result){
        //((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }

    @Test(dataProvider = "data")
    public void simple(MutableCapabilities options) throws MalformedURLException {
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        // condition particular capabilities as needed
        options.setCapability("browserVersion", "latest");
        if (options instanceof SafariOptions){
            options.setCapability("platformName", "OS X 10.12");
        } else if (options instanceof InternetExplorerOptions) {
            options.setCapability("platformName", "Windows 7");
        } else {
            options.setCapability("platformName", "Windows 10");
        }

        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("seleniumVersion", "3.11.0");
        sauceOptions.setCapability("name", "W3CTestNG");
        sauceOptions.setCapability("build", "W3C");

        options.setCapability("sauce:options", sauceOptions);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + "@ondemand.saucelabs.com/wd/hub"), options);

        GuineaPigPage page = GuineaPigPage.visitPage(driver);
        String title = page.driver.getTitle();

        Assert.assertTrue(title.contains("Sauce"));
    }
}
