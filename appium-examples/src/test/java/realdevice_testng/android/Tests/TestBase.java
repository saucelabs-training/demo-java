package realdevice_testng.android.Tests;

// import Sauce TestNG helper libraries

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import io.appium.java_client.android.AndroidDriver;
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

    public String seleniumURI = "https://us1-manual.app.testobject.com/wd/hub";
    public String buildTag = System.getenv("BUILD_TAG");
    public String username = System.getenv("SAUCE_USERNAME");
    public String accesskey = System.getenv("SAUCE_ACCESS_KEY");
    public String testobjectkey = System.getenv("TEST_OBJECT_KEY");
    public String app = "https://github.com/saucelabs-sample-test-frameworks/GuineaPig-Sample-App/blob/master/android/GuineaPigApp-debug.apk?raw=true";


    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(username, accesskey);

    /**
     * ThreadLocal variable which contains the  {@link AndroidDriver} instance which is used to perform browser interactions with.
     */
    private ThreadLocal<AndroidDriver> androidDriver = new ThreadLocal<AndroidDriver>();

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
                new Object[]{ "7", "Samsung Galaxy S8", "portrait" },
                new Object[]{ "8", "Google Pixel", "portrait"}
        };
    }

    /**
     * @return the {@link AndroidDriver} for the current thread
     */
    public AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    /**
     * @return the Sauce Job id for the current thread
     */
    public String getSessionId() {
        return sessionId.get();
    }

    /**
     * @return the {@link SauceOnDemandAuthentication} instance containing the Sauce username/access key
     */
    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
    }

    /**
     * Constructs a new {@link AndroidDriver} instance which is configured to use the capabilities defined by the browser,
     * version and os parameters, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @return
     * @throws MalformedURLException if an error occurs parsing the url
     */
    protected void createDriver(
            String platformVersion,
            String deviceName,
            String deviceOrientation,
            String methodName)
            throws MalformedURLException, UnexpectedException {
        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("testobject_api_key", testobjectkey);
        capabilities.setCapability("platformVersion", platformVersion);
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("deviceOrientation", deviceOrientation);
        capabilities.setCapability("name", methodName);
        //capabilities.setCapability("build", "Java-TestNG-Appium-Android-real-device");

        //real device-specific capabilities
        capabilities.setCapability("phoneOnly", "false");
        capabilities.setCapability("tabletOnly","false");
        capabilities.setCapability("privateDevicesOnly","false");

        if (buildTag != null) {
            capabilities.setCapability("build", buildTag);
        }

        // Launch remote browser and set it as the current thread
        androidDriver.set(new AndroidDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.us-west-1.saucelabs.com/wd/hub"),
                capabilities));

        String id = ((RemoteWebDriver) getAndroidDriver()).getSessionId().toString();
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
        androidDriver.get().quit();
    }
}
