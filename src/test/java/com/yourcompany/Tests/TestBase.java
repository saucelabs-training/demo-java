package com.yourcompany.Tests;

import com.saucelabs.common.SauceOnDemandAuthentication;

import com.saucelabs.simplesauce.SauceOptions;
import com.saucelabs.simplesauce.SauceSession;
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


@Ignore
@RunWith(ConcurrentParameterized.class)
public class TestBase implements SauceOnDemandSessionIdProvider {

    private SauceOptions options;
    private SauceSession sauce;
    private String sessionId;

    protected WebDriver driver;

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    public TestBase(SauceOptions options){
        super();
        this.options = options;
    }

    @ConcurrentParameterized.Parameters
    public static LinkedList optionsList() {
        LinkedList platforms = new LinkedList();

        platforms.add(new SauceOptions[]{new SauceOptions()});
        platforms.add(new SauceOptions[]{new SauceOptions().withFirefox()});
        platforms.add(new SauceOptions[]{new SauceOptions().withEdge()});
        platforms.add(new SauceOptions[]{new SauceOptions().withIE()});

        return platforms;
    }


    /**
     * Construct a new session using the Sauce Java bindings
     *
     */
    @Before
    public void setUp() {
        options = new SauceOptions();
        sauce = new SauceSession(options);

        driver = sauce.start();

        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /**
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }

}
