# Java Demonstration Scripts

Welcome to Java Demo Scripts, a set of examples showing how to use Sauce
Labs technologies. This repository contains everything you need to start with 
web, mobile, functional, and all other types of
Java automation.

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

[![Best Practices Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/best-practice.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/best-practice.yml)

[![JUnit 5 Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-examples.yml)

[![JUnit 4 Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/junit4.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/junit4.yml)

[![TestNg Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml)

[![Cucumber Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-cucumber-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-cucumber-examples.yml)

[![Real Devices](https://github.com/saucelabs-training/demo-java/actions/workflows/real-devices.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/real-devices.yml)

## 🥇Most Popular

* [Best practices with multiple testing strategies](./best-practice/)
* [Quick start test, Junit 5](./selenium-examples/src/test/java/com/saucedemo/selenium/demo/SeleniumTest.java)
* [Quick start test, Junit 4](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/demo/SeleniumTest.java)
* [Quick start test, TestNg](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/demo/SeleniumTest.java)
* [iOS real device, native app, Junit4](./appium-examples/src/test/java/com/appium_app/simple_example/IOSNativeAppTest.java)
* [Front-end performance testing](./selenium-examples/src/test/java/com/saucedemo/selenium/PerformanceTest.java)
* [Visual E2E test](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/SimpleVisualE2ETest.java)
* [Sauce Connect usage](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/SauceConnectTest.java)

## Best Practices

* [Desktop](./best-practice/src/test/java/com/saucedemo/tests/DesktopTests.java) `junit4` `sauce-bindings`
* [Emu/Sim Web](./best-practice/src/test/java/com/saucedemo/tests/EmuSimWebAppTests.java) `junit4` `sauce-bindings`
* [Performance](./best-practice/src/test/java/com/saucedemo/tests/PerformanceTests.java) `junit4` `sauce-bindings`
* [RealDevice](./best-practice/src/test/java/com/saucedemo/tests/RealDeviceWebTests.java) `junit4` `sauce-bindings`

## 🖥Web automation

- Sauce Bindings With TestRunner Examples
    * [Junit 5](./selenium-examples/src/test/java/com/saucedemo/selenium/demo/SaucebindingsJunitTest.java) `junit5` `sauce-bindings`
    * [Junit 4](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/demo/SauceBindingsJunit4Test.java) `junit4` `sauce-bindings`
    * [TestNg](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/demo/SauceBindingsTestngTest.java) `testng` `sauce-bindings`

- Sauce Bindings Examples
    * [Junit 5](./selenium-examples/src/test/java/com/saucedemo/selenium/demo/SauceBindingsTest.java) `junit4` `sauce-bindings`
    * [Junit 4](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/demo/SauceBindingsTest.java) `junit4` `sauce-bindings`
    * [TestNg](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/demo/SauceBindingsTest.java) `junit4` `sauce-bindings`

- Selenium Examples
    * [Accessibility Test with Sauce Bindings](/selenium-examples/src/test/java/com/saucedemo/selenium/accessibility/SauceBindingsTest.java) `junit4` `sauce-bindings`
    * [Accessibility Test with Deque Axe](/selenium-examples/src/test/java/com/saucedemo/selenium/accessibility/DequeAxeTest.java) `junit4`
    * [Cucumber web test](./selenium-cucumber-examples/src/test/java/com/saucedemo/selenium/cucumber/RunTestsAT.java)
    * [Windows authentication](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/WindowsAuthentication.java) `junit4`
    * [Cross Browser/Platform in Parallel w/ TestNG](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/CrossBrowserPlatformTest.java) `testng`
    * [Performance, front-end with Sauce Bindings](/selenium-examples/src/test/java/com/saucedemo/selenium/PerformanceTest.java) `junit5` `sauce-bindings`
    * [Single Browser in Parallel w/ TestNG](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/ParallelSingleBrowserTest.java) `testng`

## 📱Mobile automation

[📚 Mobile Testing Training Tutorials](./TRAINING.md)

- Real Devices
    * [iOS native app](./appium-examples/src/test/java/com/appium_app/simple_example/IOSNativeAppTest.java)
    * [Android native app](./appium-examples/src/test/java/com/appium_app/simple_example/AndroidNativeAppTest.java)
    * [Upload app to Sauce Storage](./appium-examples/src/test/java/com/helpers/push_apps_to_storage.sh)
    * [Image Injection](./appium-examples/src/test/java/com/appium_app/image_injection)
    * [Biometric Login](./appium-examples/src/test/java/com/appium_app/biometric_login)
    * [Cucumber w/ Appium](./appium-junit4-cucumber-examples/src/test/resources/LoginPage.feature) `junit4` `cucumber`

- Emulators and Simulators
    * [iOS native app](./appium-examples/src/test/java/com/appium_app/simple_example/IOSNativeAppTest.java)
    * [Android native app](./appium-examples/src/test/java/com/appium_app/simple_example/AndroidNativeAppTest.java)
    * [Biometric Login](./appium-examples/src/test/java/com/appium_app/biometric_login)

## ⚙️Setup

* Install [Git](https://github.com/saucelabs-training/demo-java/blob/main/docs/prerequisites.md#install-git)

* Install [IntelliJ (or another IDE)](https://github.com/saucelabs-training/demo-java/blob/main/docs/prerequisites.md#install-intellij)

* Install [JDK](https://github.com/saucelabs-training/demo-java/blob/main/docs/prerequisites.md#install-the-jdk)

* Install [Maven](https://github.com/saucelabs-training/demo-java/blob/main/docs/prerequisites.md#install-maven)

### Import the Project

1. Create a directory on your machine.

2. Clone this repository into the said directory.
    ```
    $ git clone https://github.com/saucelabs-training/demo-java.git
    ```

3. Import the project into IntelliJ (or the IDE of your choice) as a Maven Project.

4. Click through the prompts, and confirm when it asks to **Import from Sources**

5. Choose the **demo-java** directory as the **root** directory of the project.

### Set Your Sauce Labs Credentials

1. Copy your Sauce Labs **username** and **accessKey** in
   the [User Settings](https://app.saucelabs.com/user-settings) section of
   the [Sauce Labs Dashboard](https://app.saucelabs.com/dashboard/builds).
2. Open a Terminal window (command prompt for Windows) and set your Sauce Labs Environment
   variables:   
   **Mac OSX:**
   ```
   $ export SAUCE_USERNAME="your username"
   $ export SAUCE_ACCESS_KEY="your accessKey"
   ```
   **Windows:**
   ```
   > set SAUCE_USERNAME="username"
   > set SAUCE_ACCESS_KEY="accessKey"
   ```
   > To set an environment variable permanently in Windows, you must append it to the `PATH`
   variable.

   > Go to **Control Panel > System > Windows version > Advanced System Settings > Environment
   Variables > System Variables > Edit > New**

   > Then set the "Name" and "Value" for each variable

3. Test the environment variables

   **Mac OSX:**
    ```
    $ echo $SAUCE_USERNAME
    $ echo $SAUCE_ACCESS_KEY
    ```

   ***WARNING FOR UNIX USERS!***

   *If you have problems setting your environment variables, run the following commands in your
   terminal:*

    ```
    $ launchctl setenv SAUCE_USERNAME $SAUCE_USERNAME
    $ launchctl setenv SAUCE_ACCESS_KEY $SAUCE_ACCESS_KEY
    ```

   **Windows:**
    ```
    > echo %SAUCE_USERNAME%
    > echo %SAUCE_ACCESS_KEY%
    ```

### Run a Maven Test

1. Run the following command to update any package dependencies:
    ```
    $ mvn dependency:resolve
    ```
2. Then run the following command to compile your test code:
    ```
    $ mvn test-compile
    ```
3. Finally, run the following test to see if you've properly configured the test environment:
    ```
    $ mvn test -pl best-practice -Dtest=DesktopTests 

    ```

   You can run different tests from different modules. Check out some examples by looking at
   the [CI YML files](./.github/workflows)

## Contributing

Sauce Labs maintains this repository. **We welcome all ideas
and contributions!**

Guidance for contributing can be found [here](./CONTRIBUTING.md)

## Disclaimer

> The code in these scripts is provided on an "AS-IS" basis without warranty of any kind, either
> express or implied, including without limitation any implied warranties of condition,
> uninterrupted
> use, merchantability, fitness for a particular purpose, or non-infringement. These scripts are
> provided for educational and demonstration purposes only and should not be used in production.
> Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by
> the Technical Services team at Sauce Labs.
>
> Some examples in this repository, such as `appium-example`, `parallel-testing`, and `headless`,
> may require a different account tier beyond free trial. Please contact
> the [Sauce Labs Sales Team](https://saucelabs.com/contact) for support and information.
