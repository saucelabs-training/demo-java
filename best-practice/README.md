# Best Practices 

## Executing tests

1. Run full suite
```java
cd best-practice
mvn clean test
```
The output will look like this:
```text
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 140.32 s - in com.saucedemo.VisualCrossPlatformTests
[INFO] Running com.saucedemo.EmusimWebAppTests
Feb 03, 2021 3:17:44 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: OSS
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 60.017 s - in com.saucedemo.EmusimWebAppTests
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
