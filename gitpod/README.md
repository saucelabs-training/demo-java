# Selenium examples
This folder contains Gitpod examples

## How to run tests

### JUnit 5 test
```bash
cd gitpod
mvn test -Dtest=<TestName>
```

Your output will look like this if done correctly

```java
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------< com.saucelabs:gitpod >-------------------
[INFO] Building gitpod 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ gitpod ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:compile (default-compile) @ gitpod ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ gitpod ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/nikolayadvolodkin/Documents/source/java/demo-java/gitpod/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:testCompile (default-testCompile) @ gitpod ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:3.0.0-M4:test (default-test) @ gitpod ---
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

