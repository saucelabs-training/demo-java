# Selenium TestNG Examples
This folder contains Selenium examples specific to TestNG

## Examples

- [Single Browser in Parallel w/ TestNG](./src/test/java/com/saucedemo/ParallelSingleBrowserTest.java)
- [Cross Browser/Platform in Parallel w/ TestNG](./src/test/java/com/saucedemo/CrossBrowserPlatformTest.java)

## How to run tests

### Single Browser Test in Parallel
```bash
cd selenium-testng-examples
mvn test -Dtest=com.saucedemo.ParallelSingleBrowserTest
```
<details>
  <summary>Your output will look like this if done correctly</summary>

```java
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------< com.saucelabs:selenium-testng-examples >---------------
[INFO] Building selenium-testng-examples 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ selenium-testng-examples ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/chriseccleston/git/demo-java/selenium-testng-examples/src/main/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.0:compile (default-compile) @ selenium-testng-examples ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ selenium-testng-examples ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/chriseccleston/git/demo-java/selenium-testng-examples/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.0:testCompile (default-testCompile) @ selenium-testng-examples ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ selenium-testng-examples ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.saucedemo.ParallelSingleBrowserTest
May 03, 2021 2:51:16 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:17 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:17 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:17 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:17 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:17 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=b60f988ea4894883bcb7a897e46f6a17 job-name=testCase8
Test Job Link: https://app.saucelabs.com/tests/b60f988ea4894883bcb7a897e46f6a17
May 03, 2021 2:51:18 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=3d595b36a0024220bd1dcb543cfd3004 job-name=testCase2
Test Job Link: https://app.saucelabs.com/tests/3d595b36a0024220bd1dcb543cfd3004
SauceOnDemandSessionID=78c1e7bee966461eb4ae0ea7443afa65 job-name=testCase3
Test Job Link: https://app.saucelabs.com/tests/78c1e7bee966461eb4ae0ea7443afa65
SauceOnDemandSessionID=1220c775ee9041839c5b4d9edc74bbf4 job-name=testCase5
Test Job Link: https://app.saucelabs.com/tests/1220c775ee9041839c5b4d9edc74bbf4
SauceOnDemandSessionID=270f0cb1522045e4a4203303b2594237 job-name=testCase10
Test Job Link: https://app.saucelabs.com/tests/270f0cb1522045e4a4203303b2594237
SauceOnDemandSessionID=4584a4e372f2472093e056462e011529 job-name=testCase9
Test Job Link: https://app.saucelabs.com/tests/4584a4e372f2472093e056462e011529
SauceOnDemandSessionID=924e52c4fe3542e38b7acf9f479ad9e9 job-name=testCase4
Test Job Link: https://app.saucelabs.com/tests/924e52c4fe3542e38b7acf9f479ad9e9
May 03, 2021 2:51:20 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:20 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:51:21 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=3ea46655a93145bfa7544a772c36bbbd job-name=testCase1
Test Job Link: https://app.saucelabs.com/tests/3ea46655a93145bfa7544a772c36bbbd
SauceOnDemandSessionID=10f14b434e454938b1d37af3ee239955 job-name=testCase7
Test Job Link: https://app.saucelabs.com/tests/10f14b434e454938b1d37af3ee239955
SauceOnDemandSessionID=2d3aa210b61f42d092b9dc2aeb7bffcc job-name=testCase6
Test Job Link: https://app.saucelabs.com/tests/2d3aa210b61f42d092b9dc2aeb7bffcc
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 13.943 s - in com.saucedemo.ParallelSingleBrowserTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  17.427 s
[INFO] Finished at: 2021-05-03T14:51:23-04:00
[INFO] ------------------------------------------------------------------------

```
</details>

### Cross Browser/Platform Test in Parallel
```bash
cd selenium-testng-examples
mvn test -Dtest=com.saucedemo.CrossBrowserPlatformTest
```
<details>
  <summary>Your output will look like this if done correctly</summary>

```java
[INFO] Scanning for projects...
[INFO]
[INFO] ---------------< com.saucelabs:selenium-testng-examples >---------------
[INFO] Building selenium-testng-examples 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ selenium-testng-examples ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/chriseccleston/git/demo-java/selenium-testng-examples/src/main/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.0:compile (default-compile) @ selenium-testng-examples ---
[INFO] No sources to compile
[INFO]
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ selenium-testng-examples ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/chriseccleston/git/demo-java/selenium-testng-examples/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.0:testCompile (default-testCompile) @ selenium-testng-examples ---
[INFO] Changes detected - recompiling the module!
[WARNING] File encoding has not been set, using platform encoding UTF-8, i.e. build is platform dependent!
[INFO] Compiling 3 source files to /Users/chriseccleston/git/demo-java/selenium-testng-examples/target/test-classes
[INFO]
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ selenium-testng-examples ---
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.saucedemo.CrossBrowserPlatformTest
May 03, 2021 2:58:58 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:58:58 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:58:58 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=326117097bfc462aa84257085b38fc14 job-name=testCase2
Test Job Link: https://app.saucelabs.com/tests/326117097bfc462aa84257085b38fc14
SauceOnDemandSessionID=baf409ac131d4cabab9b5b05b1cb64d6 job-name=testCase2
Test Job Link: https://app.saucelabs.com/tests/baf409ac131d4cabab9b5b05b1cb64d6
May 03, 2021 2:58:59 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=61a017239e734eb9b9ecb04591747d9d job-name=testCase1
Test Job Link: https://app.saucelabs.com/tests/61a017239e734eb9b9ecb04591747d9d
May 03, 2021 2:59:01 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=87d89c9dbbba46e6a6bfe3de6e92d5b6 job-name=testCase1
Test Job Link: https://app.saucelabs.com/tests/87d89c9dbbba46e6a6bfe3de6e92d5b6
May 03, 2021 2:59:01 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=79cc4bdb8aa74a72ad04b00445edf6a3 job-name=testCase2
Test Job Link: https://app.saucelabs.com/tests/79cc4bdb8aa74a72ad04b00445edf6a3
SauceOnDemandSessionID=33bff3d777164c49a34ba956974dd0cf job-name=testCase1
Test Job Link: https://app.saucelabs.com/tests/33bff3d777164c49a34ba956974dd0cf
May 03, 2021 2:59:06 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:59:06 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
May 03, 2021 2:59:07 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=19ae3365ebd54336bba525055d0cc2c9 job-name=testCase1
Test Job Link: https://app.saucelabs.com/tests/19ae3365ebd54336bba525055d0cc2c9
SauceOnDemandSessionID=1064ada418aa4ac1b9251ed7e11df506 job-name=testCase2
Test Job Link: https://app.saucelabs.com/tests/1064ada418aa4ac1b9251ed7e11df506
May 03, 2021 2:59:08 PM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
SauceOnDemandSessionID=e23e5d84e0754ee3a8a1e4d0f418b74d job-name=testCase2
Test Job Link: https://app.saucelabs.com/tests/e23e5d84e0754ee3a8a1e4d0f418b74d
SauceOnDemandSessionID=3fcd41c0f3e64be59185ac018f8bd9d9 job-name=testCase1
Test Job Link: https://app.saucelabs.com/tests/3fcd41c0f3e64be59185ac018f8bd9d9
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 21.659 s - in com.saucedemo.CrossBrowserPlatformTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  27.310 s
[INFO] Finished at: 2021-05-03T14:59:11-04:00
[INFO] ------------------------------------------------------------------------

```
</details>
