package com.yourcompany.Tests;

import com.saucelabs.saucebindings.SauceSession;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class SauceTestWatcher extends TestWatcher {
    private SauceSession sauceSession;

    public void setSession(SauceSession session) {
        sauceSession = session;
    }

    protected void succeeded(Description description) {
        sauceSession.stop("passed");
    }

    protected void failed(Description description) {
        sauceSession.stop("failed");
    }

    protected void finished(Description description) {
        // Implement an `isFinished()` method and use API to ensure that session is stopped
    }

}
