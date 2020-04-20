package com.yourcompany.Tests;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

@Ignore
public abstract class JUnitBase {
    protected SauceOptions options;
    protected SauceSession session;
    protected WebDriver driver;

    @Rule
    public SauceTestWatcher testWatcher = new SauceTestWatcher();

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    public String getMethodName() {
        return name.getMethodName();
    }

    public void createOptions() {
        options = new SauceOptions();
        options.setName(getMethodName());
    }

    public void updateOptions() {
        // implement in subclass
    }

    @Before
    public void setUp() {
        createOptions();
        updateOptions();
        session = new SauceSession(options);
        testWatcher.setSession(session);

        driver = session.start();
    }
}
