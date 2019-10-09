package tests;

import pages.SauceDemoNavigation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

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
    protected ThreadLocal<SauceDemoNavigation> navigation = new ThreadLocal<>();

    public enum RunType { LOCAL, SAUCE }

    /**
     * @return the {@link WebDriver} for the current thread
     */
    protected WebDriver getWebDriver() {
        return webDriver.get();
    }
    protected SauceDemoNavigation getNavigation() {
        return navigation.get();
    }

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
        sauceOptions.setCapability("build", "parallel-TestNG-single-browser-demo");

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

    @BeforeMethod
    protected void createDriver(Method method) {
        String browserName = System.getProperty("browserName") == null ? "chrome" : System.getProperty("browserName");
        String browserVersion = System.getProperty("browserVersion") == null ? "73.0" : System.getProperty("browserVersion");
        String platformName = System.getProperty("platformName") == null ? "macOS 10.14" : System.getProperty("platformName");
        String methodName = method.getName();
        RunType runType = System.getProperty("runType") == null ? RunType.SAUCE : RunType.valueOf(System.getProperty("runType"));

        this.runType = runType;
        //Set up the ChromeOptions object, which will store the capabilities
        MutableCapabilities capabilities = new MutableCapabilities();

        if (browserName.equals("chrome")) {
            ChromeOptions caps = new ChromeOptions();
            caps.setExperimentalOption("w3c", true);
            capabilities = caps;
        }
        else if (browserName.equals("firefox")) {
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

        navigation.set(new SauceDemoNavigation(getWebDriver()));
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
