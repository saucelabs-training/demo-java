# Selenium examples
This folder contains Selenium examples

## Examples

- [Accessibility Test with Sauce Bindings](../selenium-examples/src/test/java/com/saucedemo/accessibility/SauceBindingsExampleTest.java)
- [Accessibility Test with Deque Axe](../selenium-examples/src/test/java/com/saucedemo/accessibility/DequeAxeExampleTest.java)
- [Simple web test, Junit 5](../selenium-examples/src/test/java/com/saucedemo/JUnit5W3CChromeTest.java)
- [Simple Headless test, TestNG](../selenium-examples/src/test/java/com/saucedemo/SampleHeadlessSauceTest.java)
- [Sauce Status Updates w/ JUnit 3](../selenium-examples/src/test/java/com/saucedemo/JUnit3UpdateSauceStatusTest.java)

## How to run tests

### JUnit 5 test
```bash
cd selenium-examples
mvn test -Dtest=JUnit5W3CChromeTest
```

Your output will look like this if done correctly

```java
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------< com.saucelabs:selenium-examples >-------------------
[INFO] Building selenium-examples 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ selenium-examples ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:compile (default-compile) @ selenium-examples ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ selenium-examples ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/nikolayadvolodkin/Documents/source/java/demo-java/selenium-examples/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:testCompile (default-testCompile) @ selenium-examples ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:3.0.0-M4:test (default-test) @ selenium-examples ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.saucedemo.JUnit5W3CChromeTest
Apr 05, 2021 1:48:11 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=c36e76bd99964b29824e3c9597558659 job-name=Junit5W3CChromeTest
Test Job Link: https://app.saucelabs.com/tests/c36e76bd99964b29824e3c9597558659
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 7.44 s - in com.saucedemo.JUnit5W3CChromeTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.728 s
[INFO] Finished at: 2021-04-05T13:48:14-04:00

```

