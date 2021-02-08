# Web Automation Best Practices 
This is a repository that contains a suite of tests entended to
test SauceDemo application.

The suite is built using a combination of testing techniques for
the most efficient test coverage. This suite combines
techniques such as:
* Functional browser automation on Desktop
* Functional mobile web automation
* Visual testing
* Front-end performance tests
* API testing

![visualTesting](/assets/visualTesting.gif)

## Executing tests

1. Run full suite
```java
cd best-practice
mvn clean test
```
The output will look like this:
```text
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 140.32 s - in com.saucedemo.tests.VisualCrossPlatformTests
[INFO] Running com.saucedemo.tests.EmusimWebAppTests
Feb 03, 2021 3:17:44 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: OSS
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 60.017 s - in com.saucedemo.tests.EmusimWebAppTests
[INFO] 
[INFO] Results:
[INFO] 
[WARNING] Tests run: 6, Failures: 0, Errors: 0, Skipped: 1
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:23 min
[INFO] Finished at: 2021-02-03T15:19:13-05:00
[INFO] ------------------------------------------------------------------------

```
2. Run visual tests
```java
cd best-practice
mvn clean test -Dtest=Visual*
```
3. Run desktop tests
```java
cd best-practice
mvn clean test -Dtest=Desktop*
```
