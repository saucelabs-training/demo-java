package com.saucedemo.selenium.junit4.demo;

import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.Assert.assertEquals;

public class SauceBindingsTest {
    private SauceSession session;
    public RemoteWebDriver driver;

    /**
     * A Test Watcher is needed to be able to get the results of a Test so that it can be sent to Sauce Labs
     * Note that the name is never actually used
     */
    @Rule
    public SauceTestWatcher watcher = new SauceTestWatcher();

    /**
     * TestName Rule allows dynamically sending name information to Sauce Labs
     */
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setup() {
        SauceOptions sauceOptions = SauceOptions.chrome()
                .setName(testName.getMethodName())
                .build();
        session = new SauceSession(sauceOptions);
        driver = session.start();
    }

    @Test
    public void correctTitle() {
        driver.navigate().to("https://www.saucedemo.com");
        assertEquals("Swag Labs", driver.getTitle());
    }

    protected class SauceTestWatcher extends TestWatcher {
        @Override
        protected void failed(Throwable e, Description description) {
            session.stop(false);
        }

        @Override
        protected void succeeded(Description description) {
            session.stop(true);
        }
    }
}
