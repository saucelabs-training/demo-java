# Sauce Labs On-Boarding Java Examples

The following scripts provide quick examples to test your connection to Sauce Labs. Each script, titled `module<#>-<framework_name>`, represents a module from the "Getting Started" tab in the Sauce labs UI.

### Module List

* [Module 1: Running your first test](#module-1-running-your-first-test)
* [Module 2: Testing against your own application](#module-2-testing-against-your-own-application)
* [Module 3: Adding setup and teardown steps](#module-3-adding-setup-and-teardown-steps)
* [Module 4: Adjusting timeouts, delays, and build tags](#module-4-adjusting-timeouts-delays-and-build-tags)

<br />

> All examples below use the TestNG test framework
>

### Module 1: Running your first test

Open the script `Module1<framework_name>Test.java`, and ensure you've exported (or hardcoded, but not recommended) your [Sauce Labs Account Credentials](https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials) in the following lines:

```
String sauceUserName = System.getenv("SAUCE_USERNAME");
String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
```

Run the following command to run the test:

```
mvn test -Dtest=Module1TestNGTest -pl on-boarding-modules/testng
```

<br />

### Module 2: Testing against your own application

Open the script `Module2<framework_name>Test.java`. Edit the following line with your own application URL:

```
driver.navigate().to("https://www.saucedemo.com");
```

Please take notice that if your application is not publicly available the test will fail to make a connection with Sauce Labs. Please read the following wiki page on how to [Setup Sauce Connect Proxy](https://wiki.saucelabs.com/display/DOCS/Sauce+Connect+Proxy) to ensure you're tests can run on Sauce Labs.

<br />

### Module 3: Adding `setup` and `teardown` Steps

Open the script `Module3<framework_name>Test.java`. Please notice how the script contains `@BeforeMethod` and `@AfterMethod` methods.

`@BeforeMethod`:
```
@BeforeMethod
    public void setupTestMEthod() throws MalformedURLException {
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");
        String sauceURL = "https://ondemand.saucelabs.com/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("platform", "Windows 10");
        capabilities.setCapability("version", "59.0");
        capabilities.setCapability("build", "Onboarding Sample App - Java-TestNG");
        capabilities.setCapability("name", "3-cross-browser");

        /** If you're accessing the EU data center, use the following endpoint:.
         * https://ondemand.eu-central-1.saucelabs.com/wd/hub
         * */
        driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
    }
```

`@AfterMethod`
```
@AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
```

This follows an automated testing best practice; setting prerequisite and postrequisite tasks for each test suite. 

In this example, we move our setup tasks (setting test capability options) and our teardown tasks (closing the Sauce Labs session and returning the test result) into separate functions.

<br />

### Module 4: Adjusting timeouts, delays, and build tags

Open the script `Module4<framework_name>Test.java`. Please notice that the following lines contain some specific `driver` capabilities:

```
sauceOpts.setCapability("maxDuration", 3600);
sauceOpts.setCapability("commandTimeout", 600);
sauceOpts.setCapability("idleTimeout", 1000);

List<String> tags = Arrays.asList("sauceDemo", "demoTest", "module4");
sauceOpts.setCapability("tags", tags);
```

These test configuration options, allow you to control how long a session will wait for a new test command (`idleTimeout`), the maximum duration for the Sauce Labs VM lifecycle  (`maxDuration`), and the ability to filter test results by specific keywords (`tags`). 

Please read the following wiki page to learn more about [setting build tags](https://wiki.saucelabs.com/display/DOCSDEV/Best+Practice%3A+Use+Build+IDs%2C+Tags%2C+and+Names+to+Identify+Your+Tests) and [controlling build timeouts](https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options#TestConfigurationOptions-MaximumTestDuration).
