package com.yourcompany.Utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mehmetgerceker on 12/9/15.
 * This will apply to tests decorated with @Test(retryAnalyzer=RetryAnalyzer.class)
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    // set your count to rerun test Maven can set a sys property which can be read here
    private AtomicInteger count = new AtomicInteger(3);

    @Override
    public boolean retry(ITestResult result) {
        if (0 < count.getAndDecrement()) {
            return true;
        }
        return false;
    }
}
