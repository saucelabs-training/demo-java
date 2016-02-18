package com.yourcompany.Tests;

import com.saucelabs.common.SauceOnDemandAuthentication;

import com.yourcompany.TestRules.RetryRule;
import com.yourcompany.Utils.SauceHelpers;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import java.net.URL;
import java.util.LinkedList;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;



/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API to mark
 * the test as passed or failed.
 *
 * @author Neil Manvar
 */
@Ignore
@RunWith(ConcurrentParameterized.class)
public class SampleSauceTestBase implements SauceOnDemandSessionIdProvider {

    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");

    public static String seleniumURI;

    public static String buildTag;
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

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
        		return String.format("%s", super.getMethodName());
        }
    };

    /**
     * Test decorated with @Retry will be run 3 times in case they fail using this rule.
     */
    @Rule
    public RetryRule rule = new RetryRule(3);

    /**
     * Represents the browser to be used as part of the test run.
     */
    protected String browser;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    protected String os;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    protected String version;
    /**
     * Represents the deviceName of mobile device
     */
    protected String deviceName;
    /**
     * Represents the device-orientation of mobile device
     */
    protected String deviceOrientation;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    protected String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    protected WebDriver driver;

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

    public SampleSauceTestBase(String os, String version, String browser, String deviceName, String deviceOrientation) {
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

        //emus
        browsers.add(new String[]{"Linux", "4.4", "android", "Android Emulator", "portrait"});

        browsers.add(new String[]{"Mac 10.10", "9.2", "iPhone", "iPhone 6", "portrait"});

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

        String methodName = name.getMethodName();
        capabilities.setCapability("name", methodName);

        //Getting the build name.
        //Using the Jenkins ENV var. You can use your own. If it is not set test will run without a build id.
        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }
        SauceHelpers.addSauceConnectTunnelId(capabilities);
        this.driver = new RemoteWebDriver(
                new URL("http://" + username+ ":" + accesskey + seleniumURI +"/wd/hub"),
                capabilities);

        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", this.sessionId, methodName);
        System.out.println(message);
    }

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

    @BeforeClass
    public static void setupClass(){
        //get the uri to send the commands to.
        seleniumURI = SauceHelpers.buildSauceUri();
        //If available add build tag. When running under Jenkins BUILD_TAG is automatically set.
        //You can set this manually on manual runs.
        buildTag = System.getenv("BUILD_TAG");
    }
}
