package com.saucedemo;

import org.junit.Rule;
import org.junit.rules.TestName;

public class TestBase {
    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();
}
