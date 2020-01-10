package com.yourcompany.Tests;

import com.saucelabs.simplesauce.SauceSession;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class SauceTestWatcher extends TestWatcher {
    private final SessionManager sessionManager;

    public SauceTestWatcher(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    private SauceSession getSession() {
        return sessionManager.getSession();
    }

    protected void succeeded(Description description) {
        getSession().stop("passed");
    }

    protected void failed(Description description) {
        getSession().stop("failed");
    }
}
