package tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
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
    protected WebDriver getWebDriver() { return webDriver.get(); }

    private void createSauceDriver(MutableCapabilities capabilities, String methodName) {
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        //Create a map of capabilities called "sauce:options", which contain the info necessary to run on Sauce
        // Labs, using the credentials stored in the environment variables. Also runs using the new W3C standard.
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accesskey);
        sauceOptions.setCapability("seleniumVersion", "3.141.59");
        sauceOptions.setCapability("name", methodName);
        sauceOptions.setCapability("build", "parallel-TestNG-cross-browser-and-platform-demo");

        //Assign the Sauce Options to the base capabilities
        capabilities.setCapability("sauce:options", sauceOptions);

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

    private void createLocalDriver(MutableCapabilities capabilities) {
        webDriver.set(new ChromeDriver((ChromeOptions) capabilities));
    }

    /**
     * DataProvider that sets the browser combinations to be used.
     *
     * @param beforeMethod
     * @return TestNG's preferred Object[][] structure, containing browser, version, and platform information
     */
    @DataProvider(name = "sauceBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method beforeMethod) {
        RunType runType = RunType.SAUCE;
        return new Object[][]{
                /** Uncomment the other data providers ONLY if you have the relevant Sauce VM concurrency **/
                new Object[]{"chrome", "73.0", "macOS 10.14", runType},
                /*new Object[]{"chrome", "72.0", "Windows 10", runType},
                new Object[]{"chrome", "71.0", "Windows 7", runType},
                new Object[]{"chrome", "70.0", "macOS 10.14", runType},
                new Object[]{"chrome", "70.0", "macOS 10.14", runType},
                new Object[]{"chrome", "71.0", "Windows 10", runType},
                new Object[]{"chrome", "72.0", "Windows 7", runType},
                new Object[]{"chrome", "73.0", "macOS 10.14", runType},
                new Object[]{"chrome", "72.0", "macOS 10.14", runType},
                new Object[]{"chrome", "73.0", "Windows 10", runType},
                new Object[]{"chrome", "70.0", "Windows 7", runType},
                new Object[]{"chrome", "71.0", "macOS 10.14", runType},*/
                new Object[]{"firefox", "66.0", "Windows 7", runType},
                /*new Object[]{"firefox", "65.0", "Windows 10", runType},
                new Object[]{"firefox", "64.0", "macOS 10.14", runType},
                new Object[]{"firefox", "63.0", "macOS 10.13", runType},
                new Object[]{"firefox", "62.0", "macOS 10.12", runType},
                new Object[]{"firefox", "61.0", "macOS 10.13", runType},*/
        };
    }

    @DataProvider(name = "localBrowsers")
    public static Object[][] localBrowserDataProvider(Method beforeMethod) {
        RunType runType = RunType.LOCAL;
        return new Object[][]{
                new Object[]{"chrome", "latest-1", "Windows 10", runType},
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
    protected void createDriver(String browser, String browserVersion, String platformName, String methodName, RunType runType) {
        this.runType = runType;
        //Set up the ChromeOptions object, which will store the capabilities
        MutableCapabilities capabilities = new MutableCapabilities();

        if (browser.equals("chrome")) {
            ChromeOptions caps = new ChromeOptions();
            caps.setExperimentalOption("w3c", true);
            capabilities = caps;
        }
        else if (browser.equals("firefox")) {
            capabilities = new FirefoxOptions();
        }

        capabilities.setCapability("browserVersion", browserVersion);
        capabilities.setCapability("platformName", platformName);

        switch(runType) {
            case LOCAL:
                createLocalDriver(capabilities);
                break;
            case SAUCE:
                createSauceDriver(capabilities, methodName);
                break;
        }
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

    void annotate(String text) {
        if (runType.equals(RunType.SAUCE)) {
            ((JavascriptExecutor) webDriver.get()).executeScript("sauce:context=" + text);
        }
    }
}
