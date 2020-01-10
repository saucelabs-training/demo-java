package com.yourcompany.Tests;

import com.saucelabs.simplesauce.Browser;
import com.saucelabs.simplesauce.SauceOptions;
import com.saucelabs.simplesauce.SaucePlatform;
import com.saucelabs.simplesauce.SauceSession;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

@Ignore
public class TestBase implements SessionManager {

    @Rule
    public SauceTestWatcher testWatcher = new SauceTestWatcher(this);

    private SauceSession session;
    private String platform;

    protected WebDriver driver;

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    @Before
    public void setUp() {
        SauceOptions options = new SauceOptions();
        options.setName(name.getMethodName());

        if (System.getenv("START_TIME") != null) {
            options.setBuild("Build Time: " + System.getenv("START_TIME"));
        }

        if (System.getProperty("platform") != null) {
            platform = System.getProperty("platform");
        } else {
            platform = "default";
        }

        switch(platform) {
            case "windows_10_edge":
                options.setPlatformName(SaucePlatform.WINDOWS_10);
                options.setBrowserName(Browser.EDGE);
                break;
            case "mac_sierra_chrome":
                options.setPlatformName(SaucePlatform.MAC_SIERRA);
                options.setBrowserName(Browser.CHROME);
                break;
            case "windows_8_ff":
                options.setPlatformName(SaucePlatform.WINDOWS_8);
                options.setBrowserName(Browser.FIREFOX);
                break;
            case "windows_8_1_ie":
                options.setPlatformName(SaucePlatform.WINDOWS_8_1);
                options.setBrowserName(Browser.INTERNET_EXPLORER);
                break;
            case "mac_mojave_safari":
                options.setPlatformName(SaucePlatform.MAC_MOJAVE);
                options.setBrowserName(Browser.SAFARI);
                break;
            default:
                // accept Sauce defaults
                break;
        }

        session = new SauceSession(options);

        driver = session.start();
    }

    @Override
    public SauceSession getSession() {
        return session;
    }
}
