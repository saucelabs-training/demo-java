package com.yourcompany;

import com.gargoylesoftware.htmlunit.javascript.host.Console;
import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.LinkedList;

import static org.junit.Assert.*;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API to mark
 * the test as passed or failed.
 *
 * @author Neil Manvar
 */
@RunWith(ConcurrentParameterized.class)
public class SampleSauceTest implements SauceOnDemandSessionIdProvider {

    public String username = System.getenv("SAUCE_USER_NAME") != null ? System.getenv("SAUCE_USER_NAME") : System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_API_KEY") != null ? System.getenv("SAUCE_API_KEY") : System.getenv("SAUCE_ACCESS_KEY");

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @Rule public TestName name = new TestName();

    /**
     * Represents the browser to be used as part of the test run.
     */
    private String browser;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    private String os;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    private String version;
    /**
     * Represents the deviceName of mobile device
     */
    private String deviceName;
    /**
     * Represents the device-orientation of mobile device
     */
    private String deviceOrientation;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver wd;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param os
     * @param version
     * @param browser
     * @param deviceName
     * @param deviceOrientation
     */

    public SampleSauceTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
        this.deviceName = deviceName;
        this.deviceOrientation = deviceOrientation;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();

        // windows 7, Chrome 41
        browsers.add(new String[]{"OS X 10.10", "41", "chrome", null, null});

        // Windows 8.1, IE 11
         browsers.add(new String[]{"Windows 8.1", "11", "internet explorer", null, null});

         // Windows XP, IE 8
         browsers.add(new String[]{"Windows 7", "10", "internet explorer", null, null});

         // Linux, Firefox 37
         browsers.add(new String[]{"Linux", "37", "firefox", null, null});

        return browsers;
    }


    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #browser},
     * {@link #version} and {@link #os} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser != null) capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) capabilities.setCapability(CapabilityType.VERSION, version);
        if (deviceName != null) capabilities.setCapability("deviceName", deviceName);
        if (deviceOrientation != null) capabilities.setCapability("device-orientation", deviceOrientation);

        capabilities.setCapability(CapabilityType.PLATFORM, os);
        capabilities.setCapability("name", name.getMethodName());

        this.wd = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() +
                        "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) wd).getSessionId()).toString();
    }

    /**
     * Runs a simple test verifying the UI and title of the belk.com home page.
     * @throws Exception
     */
    @Test
    public void searchForSFHotel() {
    	wd.get("http://www.priceline.com/");

        WebDriverWait wait = new WebDriverWait(wd, 20);
        WebElement element;

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("hotel-dest")));
        element.click();
        element.clear();
        element.sendKeys("San Francisco, CA");

        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("img.ui-datepicker-trigger")));
        element.click();

        wd.findElement(By.xpath("//a[@class='ui-state-default'][text()=17]")).click();
        wd.findElement(By.xpath("//a[@class='ui-state-default'][text()=24]")).click();

        wd.findElement(By.id("hotel-btn-submit-retl")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Holiday Inn Express San Francisco Airport South")));
    }

    @Test
    public void SFONorthTest() {
        wd.get("http://www.priceline.com/");
        wd.findElement(By.xpath("//div[@class='xdeals-container']//a[.='Search Express Deals®']")).click();
        wd.findElement(By.id("sopq-hotel-checkout")).click();
        wd.findElement(By.linkText("17")).click();
        wd.findElement(By.id("sopq-hotel-dest")).click();
        wd.findElement(By.id("sopq-hotel-dest")).clear();
        wd.findElement(By.id("sopq-hotel-dest")).sendKeys("San Francisco, CA");
        wd.findElement(By.id("hotel-btn-submit-sopq")).click();

        WebDriverWait wait = new WebDriverWait(wd, 20);

        wait.until(ExpectedConditions.textToBePresentInElement(wd.findElement(By.tagName("html")), "SFO North - San Bruno Area Hotel"));
    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        wd.quit();
    }

    /**
     *
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}
