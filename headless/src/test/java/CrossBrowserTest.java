import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class CrossBrowserTest {
    // ThreadLocal variable containing WebDriver instance and the Sauce Job Id
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private ThreadLocal<String> sessionId = new ThreadLocal<>();

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
        sauceOptions.setCapability("build", "Sample Headless Tests");

        //Assign the Sauce Options to the base capabilities
        capabilities.setCapability("sauce:options", sauceOptions);

        //Create a new RemoteWebDriver, which will initialize the test execution on Sauce Labs servers
        String SAUCE_REMOTE_URL = "https://ondemand.us-east-1.saucelabs.com/wd/hub";
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

    @DataProvider(name = "sauceBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method beforeMethod) {
        return new Object[][]{
                /** Uncomment the other data providers ONLY if you have the relevant Sauce VM concurrency **/
                new Object[]{"chrome", "latest", "Linux"},
                new Object[]{"chrome", "latest-1", "Linux"},
                new Object[]{"firefox", "latest", "Linux"},
                new Object[]{"firefox", "latest-1", "Linux"}
        };
    }


    protected void createDriver(String browser, String browserVersion, String platformName, String methodName) {
        //Set up the ChromeOptions object, which will store the capabilities
        MutableCapabilities capabilities = new MutableCapabilities();

        if (browser.equals("chrome")) {
            ChromeOptions caps = new ChromeOptions();
            caps.setExperimentalOption("w3c", true);
            capabilities = caps;
        } else if (browser.equals("firefox")) {
            capabilities = new FirefoxOptions();
        }

        capabilities.setCapability("browserVersion", browserVersion);
        capabilities.setCapability("platformName", platformName);
        createSauceDriver(capabilities, methodName);

    }
    @Test(dataProvider = "sauceBrowsers")
    public void headlessTest(String browser, String browserVersion, String platformName) {
        this.createDriver(browser, browserVersion, platformName, "headless-parallel-cross-browser-test-java");

        /* Goes to Sauce Lab's demo page and prints title */

        this.getWebDriver().get("https://www.saucedemo.com");
        //System.out.println("title of page is: " + this.getWebDriver().getTitle());
        Assert.assertEquals(this.getWebDriver().getTitle(), "Swag Labs" );
    }
    /* Sends results to SauceLabs.com */
    @AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        try {
            ((JavascriptExecutor) webDriver.get()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        }
        finally {
            webDriver.get().quit();
        }
    }
}
