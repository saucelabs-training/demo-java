package com.saucedemo.selenium.cucumber;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

/**
 * Retries failed tests, since Surefire's rerunFailingTestsCount does not support TestNG. Reads the
 * same property so retries are enabled the same way as in the JUnit modules:
 * mvn test -Dsurefire.rerunFailingTestsCount=2
 */
public class RetryListener implements IAnnotationTransformer {
  @Override
  public void transform(
      ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
    annotation.setRetryAnalyzer(Analyzer.class);
  }

  public static class Analyzer implements IRetryAnalyzer {
    private static final int MAX_RETRIES =
        Integer.getInteger("surefire.rerunFailingTestsCount", 0);
    private int retries = 0;

    @Override
    public boolean retry(ITestResult result) {
      return retries++ < MAX_RETRIES;
    }
  }
}
