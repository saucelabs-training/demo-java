# Java Demonstration Code

Welcome to Java Demo Scripts designed by Solution Architects to provide examples of how to use Sauce Labs technologies.
This repository contains
everything that you need to get started with web, mobile, functional, performance, accessibility and all other types of
automation using Java.

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Selenium JUnit 5 Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-examples.yml)
[![JUnit 4 Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/junit4.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/junit4.yml)
[![TestNg Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml)
[![Real Devices](https://github.com/saucelabs-training/demo-java/actions/workflows/real-devices.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/real-devices.yml)

## 🖥Desktop browser automation

There are 3 main test runners people use with Java:

* [Junit 5](./selenium/src/test/java/com/saucedemo/selenium/) `junit5` `sauce-bindings`
* [Junit 4](./selenium-junit4/src/test/java/com/saucedemo/selenium/junit4/) `junit4` `sauce-bindings`
* [TestNg](./selenium-testng/src/test/java/com/saucedemo/selenium/testng/) `testng` `sauce-bindings`

### Selenium Features

* Running tests in Parallel
* Running Cross Browser/Platform
* RemoteWebDriverBuilder
* Recommended Capabilities
* Firefox-only Features
* Chrome-only Features
* Chrome DevTools

### Sauce Labs Features

* [Performance, front-end with Sauce Bindings](/selenium/src/test/java/com/saucedemo/selenium/sauce_features/PerformanceTest.java) `junit5` `sauce-bindings`
* [Accessibility Test with Deque Axe](/selenium/src/test/java/com/saucedemo/selenium/sauce_features/AccessibilityTest.java) `junit4`

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

2. Clone this repository into said directory.
    ```
    $ git clone https://github.com/saucelabs-training/demo-java.git
    ```

3. Import the project into your IntelliJ (or IDE of your choice) as a **Maven Project**.

4. Click through the prompts, and confirm when it asks to **Import from Sources**

5. Choose the **demo-java** directory as the **root** directory of the project.

### Set Your Sauce Labs Credentials

1. Copy your Sauce Labs **username** and **accessKey** in the [User Settings](https://app.saucelabs.com/user-settings)
   section of the [Sauce Labs Dashboard](https://app.saucelabs.com/dashboard/builds).
2. Open a Terminal window (command prompt for Windows) and set your Sauce Labs Environment variables:   
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
   > To set an environment variables permanently in Windows, you must append it to the `PATH` variable.

   > Go to **Control Panel > System > Windows version > Advanced System Settings > Environment Variables > System
   Variables > Edit > New**

   > Then set the "Name" and "Value" for each variable

3. Test the environment variables

   **Mac OSX:**
    ```
    $ echo $SAUCE_USERNAME
    $ echo $SAUCE_ACCESS_KEY
    ```

   ***WARNING FOR UNIX USERS!***

   *If you have problems setting your environment variables, run the following commands in your terminal:*

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

This repository is maintained by the Solutions Architect team at Sauce Labs. **We welcome all ideas and contributions!**

Guidance for contributing can be found [here](./CONTRIBUTING.md)

## Disclaimer

> The code in these scripts is provided on an "AS-IS" basis without warranty of any kind, either express or implied,
> including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a
> particular purpose, or non-infringement. These scripts are provided for educational and demonstration purposes only, and
> should not be used in production. Issues regarding these scripts should be submitted through GitHub. These scripts are
> maintained by the Technical Services team at Sauce Labs.
>
> Some examples in this repository, such as `appium-example`, `parallel-testing`, and `headless`, may require a
> different account tier beyond free trial. Please contact the [Sauce Labs Sales Team](https://saucelabs.com/contact) for
> support and information.
