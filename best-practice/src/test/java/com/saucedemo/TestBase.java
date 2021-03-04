package com.saucedemo;

import org.junit.Rule;
import org.junit.rules.TestName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestBase {
    @Rule
    public TestName testName = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };
    @Rule
    public SauceTestWatcher resultReportingTestWatcher = new SauceTestWatcher();

    public static String buildName = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
}
