package com.saucedemo.selenium.junit5;

import com.saucelabs.saucebindings.SauceSession;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SauceBindingsTest {
    private SauceSession session;
    protected RemoteWebDriver driver;

    /**
     * @RegisterExtension is needed to add a Test Watcher to a class
     * a Test Watcher is needed to be able to get the results of a Test so that it can be sent to Sauce Labs
     * Note that the name is never actually used
     */
    @RegisterExtension
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @BeforeEach
    public void setup(TestInfo testInfo) {
        SauceOptions sauceOptions = SauceOptions.chrome()
                .setName(testInfo.getDisplayName()).build();
        session = new SauceSession(sauceOptions);
        driver = session.start();
    }

    /**
     * @DisplayName is a JUnit 5 annotation that defines test case name.
     */
    @DisplayName("Sauce Bindings example with JUnit5")
    @Test
    public void SauceBindingsWithJUnit5Test() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        Assertions.assertEquals("Swag Labs", driver.getTitle());
    }

    public class SauceTestWatcher implements TestWatcher {
        @Override
        public void testSuccessful(ExtensionContext context) {
            session.stop(true);
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            session.stop(false);
        }
    }
}
