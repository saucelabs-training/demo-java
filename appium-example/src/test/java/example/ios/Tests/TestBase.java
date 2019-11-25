package example.ios.Tests;

// import Sauce TestNG helper libraries

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

// import testng annotations
// import java libraries

/**
 * Simple TestNG test which demonstrates being instantiated via a DataProvider in order to supply multiple browser combinations.
 *
 * @author Neil Manvar
 */
@Listeners({SauceOnDemandTestListener.class})
public class TestBase implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

    public String seleniumURI = "@ondemand.saucelabs.com:443";
    public String buildTag = System.getenv("BUILD_TAG");
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    public String app = "https://github.com/saucelabs-training/demo-java/blob/master/appium-example/resources/ios/SauceGuineaPig-sim-debug.app.zip?raw=true";
    //public String app = "../../../../../../resources/ios/SauceGuineaPig-sim-debug.app.zip";
    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

    /**
     * ThreadLocal variable which contains the  {@link IOSDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<IOSDriver> iosDriver = new ThreadLocal<IOSDriver>();

    /**
     * ThreadLocal variable which contains the Sauce Job Id.
     */
    private ThreadLocal<String> sessionId = new ThreadLocal<String>();

    /**
     * DataProvider that explicitly sets the browser combinations to be used.
     *
     * @param testMethod
     * @return Two dimensional array of objects with browser, version, and platform information
     */
    @DataProvider(name = "hardCodedBrowsers", parallel = true)
    public static Object[][] sauceBrowserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"iOS", "iPhone X Simulator", "12.2", "1.13.0", "portrait"},
                new Object[]{"iOS", "iPad Pro (12.9 inch) Simulator", "12.2", "1.13.0", "portrait"}
        };
    }

    /**
     * @return the {@link iosDriver} for the current thread
     */
    public IOSDriver getiosDriver() {
        return iosDriver.get();
    }

    /**
     *
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     *
     * @return the {@link SauceOnDemandAuthentication} instance containing the Sauce username/access key
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    /**
     * Constructs a new {@link IOSDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @param platformName      name of the platformName. (Android, iOS, etc.)
     * @param deviceName        name of the device
     * @param platformVersion   Os version of the device
     * @param appiumVersion     appium version
     * @param deviceOrientation device orientation
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */
    protected void createDriver(
            String platformName,
            String deviceName,
            String platformVersion,
            String appiumVersion,
            String deviceOrientation,
            String methodName)
            throws MalformedURLException, UnexpectedException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("browserName", "");
        capabilities.setCapability("deviceOrientation", deviceOrientation);
        capabilities.setCapability("appiumVersion", appiumVersion);
        capabilities.setCapability("name", methodName);
        capabilities.setCapability("build","Java-TestNG-Appium-iOS");
        capabilities.setCapability("app", app);

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        // Launch remote browser and set it as the current thread
        iosDriver.set(new IOSDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + seleniumURI +"/wd/hub"),
                capabilities));

        String id = ((RemoteWebDriver) getiosDriver()).getSessionId().toString();
        sessionId.set(id);
    }

    /**
     * Method that gets invoked after test.
     * Dumps browser log and
     * Closes the browser
     */
    @AfterMethod
    public void tearDown() throws Exception {

        //Gets browser logs if available.
        iosDriver.get().quit();
    }
}
