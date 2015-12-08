package com.yourcompany.TestRules;


import org.junit.rules.MethodRule;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mehmetgerceker on 12/7/15.
 */
public class RetryRule implements TestRule {

    private AtomicInteger retryCount;

    public RetryRule(int retries){
        super();
        this.retryCount = new AtomicInteger(retries);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        //statement is a private method which will return a new statement
        //here Statement is taken as abstract for your test which includes test method and before/after methods )
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;

                // implement retry logic here
                while (retryCount.getAndDecrement() > 0) {
                    try {
                        base.evaluate();
                        return;
                    } catch (Throwable t) {
                        if (retryCount.get() > 0 && description.getAnnotation(Retry.class)!= null) {
                            caughtThrowable = t;
                            System.err.println(description.getDisplayName() +
                                    ": Failed, " +
                                    retryCount.toString() +
                                    "retries remain");
                        } else {
                            throw caughtThrowable;
                        }
                    }
                }
            }
        };
    }
}
