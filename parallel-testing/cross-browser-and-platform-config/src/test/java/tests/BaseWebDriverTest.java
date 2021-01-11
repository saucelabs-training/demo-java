package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 */
public class BaseWebDriverTest {
    private RunType runType;

    // ThreadLocal variable containing WebDriver instance and the Sauce Job Id
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

    public enum RunType { LOCAL, SAUCE }

    /**
     * @return the {@link WebDriver} for the current thread
     */
    protected WebDriver getWebDriver() {
        return webDriver.get();
    }

    private void createSauceDriver(DesiredCapabilities capabilities, String methodName) {
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        //Create a map of capabilities called "sauce:options", which contain the info necessary to run on Sauce
        // Labs, using the credentials stored in the environment variables. Also runs using the new W3C standard.

        capabilities.setCapability("username", username);
        capabilities.setCapability("accessKey", accesskey);
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("build", "parallel-emusim-demo");


        //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
        String SAUCE_REMOTE_URL = "https://ondemand.saucelabs.com/wd/hub";
        try {
            webDriver.set(new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), capabilities));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        sessionId.set(((RemoteWebDriver)webDriver.get()).getSessionId().toString());

        // set current sessionId
        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    /**
     * DataProvider that sets the browser combinations to be used.
     *
     * @param beforeMethod
     * @return TestNG's preferred Object[][] structure, containing browser, version, and platform information
     */
    @DataProvider(name = "sauceBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method beforeMethod) {

        return new Object[][]{
                /** Uncomment the other data providers ONLY if you have the relevant Sauce VM concurrency **/
                new Object[]{"Chrome", "Android", "10.0", "Portrait", "Google Pixel 3 GoogleAPI Emulator"},
                new Object[]{"Safari", "iOS", "14.0", "Portrait", "iPhone 11 Pro Simulator"}

        };
    }

    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @param browser Represents the browser to be used as part of the test run.
     * @param browserVersion Represents the version of the browser to be used as part of the test run.
     * @param platformName Represents the operating system to be used as part of the test run.
     * @param methodName Represents the name of the test case that will be used to identify the test on Sauce.
     */
    protected void createDriver(String browser, String platformName, String platformVersion, String deviceOrientation, String deviceName, String methodName) {
        this.runType = RunType.SAUCE;
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceOrientation", deviceOrientation);
        capabilities.setCapability("deviceName", deviceName);

        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        capabilities.setCapability("name", methodName);
        capabilities.setCapability("build", "parallel-emusim-demo");


        //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + "@ondemand.saucelabs.com/wd/hub";
        try {
            webDriver.set(new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), capabilities));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        sessionId.set(((RemoteWebDriver)webDriver.get()).getSessionId().toString());

        // set current sessionId
        String id = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    //Method that gets invoked after test
    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            if (runType.equals(RunType.SAUCE)) {
                ((JavascriptExecutor) webDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
            }
        }
        finally {
            webDriver.get().quit();
        }
    }
}
