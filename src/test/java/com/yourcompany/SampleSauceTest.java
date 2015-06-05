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
    private WebDriver driver;

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
        browsers.add(new String[]{"Windows 7", "41", "chrome", null, null});

        // windows xp, IE 8
        browsers.add(new String[]{"Windows XP", "8", "internet explorer", null, null});

        // windows 7, IE 9
        browsers.add(new String[]{"Windows 7", "9", "internet explorer", null, null});

        // windows 8, IE 10
        browsers.add(new String[]{"Windows 8", "10", "internet explorer", null, null});

        // windows 8.1, IE 11
        browsers.add(new String[]{"Windows 8.1", "11", "internet explorer", null, null});

        // OS X 10.8, Safari 6
        browsers.add(new String[]{"OSX 10.8", "6", "safari", null, null});

        // OS X 10.9, Safari 7
        browsers.add(new String[]{"OSX 10.9", "7", "safari", null, null});

        // OS X 10.10, Safari 7
        browsers.add(new String[]{"OSX 10.10", "8", "safari", null, null});

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

        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() +
                        "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

        this.driver.get("http://www.belk.com/");
    }

    /**
     * Runs a simple test verifying the UI and title of the belk.com home page.
     * @throws Exception
     */
    @Test
    public void verifyBelkHompage() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10); // wait for a maximum of 5 seconds
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".primary-nav")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".promo-utility")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".logo")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#shoppingBagPlaceHolder")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#global_search_box")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".container_24")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".container_24 .hero")));

        assertTrue(driver.getTitle().equals("Home - belk.com - Belk.com"));
    }

    /**
     * Go to belk.com, click women in navigation menu, and verify UI
     * @throws Exception
     */
    @Test
    public void verifyWomenTab() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10); // wait for a maximum of 5 seconds
        WebElement womenTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#Women a")));
        womenTab.click();

        By selector = By.cssSelector(".collapsibleNav .firstSubnavHeading");
        WebElement firstSection = wait.until(ExpectedConditions.presenceOfElementLocated(selector));

        String text = firstSection.getText();

        assertTrue(text.contains("Shorts & Capris") && text.contains("Skirts"));

        // Special sizes
        selector = By.cssSelector(".collapsibleNav .firstSubnavHeading + li");
        WebElement specialSizesSection = wait.until(ExpectedConditions.presenceOfElementLocated(selector));

        assertTrue(specialSizesSection.getText().contains("Women's Plus"));

        // we should see the vertical image
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#vertical-hero")));

        // TO DO: verify other elements

        // make sure url and title has changed
        assertTrue(driver.getTitle().equals("Women - Belk.com"));
        assertTrue(driver.getCurrentUrl().equals("http://www.belk.com/AST/Main/Belk_Primary/Women.jsp"));

        // verify women tab is selected
        assertTrue(driver.findElement(By.cssSelector(".current")).getText().contains("Women"));
    }

    /**
     * Go to belk.com, click Beauty & Fragrance in navigation menu, and verify UI
     * @throws Exception
     */
    @Test
    public void verifyBeautyAndFragrance() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10); // wait for a maximum of 5 seconds
        WebElement beautyFragranceTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#Beauty_And_Fragrance a")));
        beautyFragranceTab.click();

        By selector = By.cssSelector(".collapsibleNav .firstSubnavHeading");
        WebElement firstSection = wait.until(ExpectedConditions.presenceOfElementLocated(selector));

        String text = firstSection.getText();

        assertTrue(text.contains("Bath & Body") && text.contains("Fragrance") && text.contains("Makeup"));

        // Features
        selector = By.cssSelector(".collapsibleNav .firstSubnavHeading + li");
        WebElement specialSizesSection = wait.until(ExpectedConditions.presenceOfElementLocated(selector));

        text = specialSizesSection.getText();
        assertTrue(text.contains("What's New") && text.contains("Allure Best of Beauty"));

        // we should see the vertical image
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#horizontal-hero")));

        // TO DO: verify other elements

        // make sure url and title has changed
        assertTrue(driver.getTitle().equals("Beauty And Fragrance - Belk.com"));
        assertTrue(driver.getCurrentUrl().equals("http://www.belk.com/AST/Main/Belk_Primary/Beauty_And_Fragrance.jsp"));

        // verify women tab is selected
        text = driver.findElement(By.cssSelector(".current")).getText();
        assertTrue(text.contains("Beauty &") && text.contains("Fragrance"));
    }

    /**
     * Go to belk.com, click sigin/register in top bar, and verify UI
     * @throws Exception
     */
    @Test
    public void verifySignInRegisterPage() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, 10); // wait for a maximum of 5 seconds
        WebElement signInRegisterLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".hide-logged-in a")));
        signInRegisterLink.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("returningRadio")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[value='2']")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txt_email_address_n")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txt_email_address_n")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("txt_password_n")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("forgot_Password")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("#signInButton")));

        assertTrue(driver.getTitle().equals("Sign In/Register - Belk.com"));
        assertTrue(driver.getCurrentUrl().equals("https://www.belk.com/AST/Misc/Belk_Stores/Global_Navigation/Sign_In_Register.jsp"));
    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
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
