# Java Demonstration Scripts

Welcome to Java Demo Scripts, a set of examples showing how to use the features of Sauce
Labs. This repository contains everything you need to start with web, mobile, functional, and all
other types of
Java automation.

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)

[![Best Practices Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/best-practice.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/best-practice.yml)

[![JUnit 5 Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-junit5-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-junit5-examples.yml)

[![JUnit 4 Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-junit4-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-junit4-examples.yml)

[![TestNG Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml)

[![Cucumber Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-cucumber-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/selenium-cucumber-examples.yml)

[![Appium Examples - Real Devices](https://github.com/saucelabs-training/demo-java/actions/workflows/appium-app-examples.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/appium-app-examples.yml)

## 🥇Most Popular

* [Best practices with multiple testing strategies](./best-practice/)
* [Quick start test, Junit 5](./selenium-junit5-examples/src/test/java/com/saucedemo/selenium/demo/CartTest.java)
* [Quick start test, Junit 4](./selenium-junit4-examples/src/test/java/com/saucedemo/selenium/junit4/demo/CartTest.java)
* [Quick start test, TestNg](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/demo/SeleniumTest.java)
* [iOS real device, native app](./appium/appium-app/appium-app-examples/src/test/java/com/examples/simple_example/IOSNativeAppTest.java)
* [Front-end performance testing](./selenium-junit5-examples/src/test/java/com/saucedemo/selenium/sauce_features/PerformanceTest.java)

## Best Practices

* [Desktop](./best-practice/src/test/java/com/saucedemo/tests/DesktopTests.java) `junit4`
  `sauce-bindings`
* [Emu/Sim Web](./best-practice/src/test/java/com/saucedemo/tests/EmuSimWebAppTests.java) `junit4`
  `sauce-bindings`
* [Performance](./best-practice/src/test/java/com/saucedemo/tests/PerformanceTests.java) `junit4`
  `sauce-bindings`
* [RealDevice](./best-practice/src/test/java/com/saucedemo/tests/RealDeviceWebTests.java) `junit4`
  `sauce-bindings`

## 🖥Web automation

- Selenium Examples
    * [Accessibility Test with Deque axe-core](./selenium-junit5-examples/src/test/java/com/saucedemo/selenium/sauce_features/AccessibilityTest.java)
      `junit5`
    * [Cucumber web test](./selenium-cucumber-examples/src/test/java/com/saucedemo/selenium/cucumber/RunTestsAT.java)
      `cucumber` `bdd`
    * [Cross Browser/Platform in Parallel w/ TestNG](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/CrossBrowserPlatformTest.java)
      `testng`
    * [Performance, front-end with Sauce Bindings](./selenium-junit5-examples/src/test/java/com/saucedemo/selenium/sauce_features/PerformanceTest.java)
      `junit5` `sauce-bindings`
    * [Single Browser in Parallel w/ TestNG](./selenium-testng-examples/src/test/java/com/saucedemo/selenium/testng/ParallelSingleBrowserTest.java)
      `testng`

## 📱Mobile automation

[📚 Mobile Testing Training Tutorials](./TRAINING.md)

- Real Devices
    * [iOS native app](./appium/appium-app/appium-app-examples/src/test/java/com/examples/simple_example/IOSNativeAppTest.java)
    * [Android native app](./appium/appium-app/appium-app-examples/src/test/java/com/examples/simple_example/AndroidNativeAppTest.java)
    * [Upload app to Sauce Storage](./appium/appium-app/appium-app-examples/src/test/java/com/helpers/push_apps_to_storage.sh)
    * [Image Injection](./appium/appium-app/appium-app-examples/src/test/java/com/examples/image_injection)
    * [Biometric Login](./appium/appium-app/appium-app-examples/src/test/java/com/examples/biometric_login)
    * [Deep Link](./appium/appium-app/appium-app-examples/src/test/java/com/examples/deep_link)
    * [Find By Image](./appium/appium-app/appium-app-examples/src/test/java/com/examples/find_by_image)
    * [Gestures](./appium/appium-app/appium-app-examples/src/test/java/com/examples/gestures)
    * [Mid-Session App Installs](./appium/appium-app/appium-app-examples/src/test/java/com/examples/mid_session_app_installs)
    * [Network Throttling](./appium/appium-app/appium-app-examples/src/test/java/com/examples/network_throttling)
    * [Upload/Download File](./appium/appium-app/appium-app-examples/src/test/java/com/examples/up_download_file)
    * [Allowlist](./appium/appium-app/appium-app-examples/src/test/java/com/examples/allowlist)
    * [Cucumber w/ Appium](./appium-junit4-cucumber-examples/src/test/resources/LoginPage.feature)
      `junit4` `cucumber`

- Emulators and Simulators
    * [iOS native app](./appium/appium-app/appium-app-examples/src/test/java/com/examples/simple_example/IOSNativeAppTest.java)
    * [Android native app](./appium/appium-app/appium-app-examples/src/test/java/com/examples/simple_example/AndroidNativeAppTest.java)
    * [Biometric Login](./appium/appium-app/appium-app-examples/src/test/java/com/examples/biometric_login)

- Best Practice
    * [iOS Best Practice](./appium/appium-app/appium-app-best-practice/src/test/java/com/saucelabs/tests/iOS/DemoSimpleTest.java)

- Mobile Web
    * [Mobile Web Tests](./appium/mobile-web/)

- Android App
    * [Android App Tests](./appium/android-app/)

## 🎭 Playwright automation

- Playwright Examples
    * [Authentication Test](./playwright-examples/src/test/java/com/saucedemo/playwright/AuthenticationTest.java)
      `junit5`
    * [Cart Test](./playwright-examples/src/test/java/com/saucedemo/playwright/CartTest.java)
      `junit5`
    * [Checkout Test](./playwright-examples/src/test/java/com/saucedemo/playwright/CheckoutTest.java)
      `junit5`
    * [Navigation Test](./playwright-examples/src/test/java/com/saucedemo/playwright/NavigationTest.java)
      `junit5`
    * [Standalone Test](./playwright-examples/src/test/java/com/saucelabs/StandaloneTest.java)
      `junit5`

## ☁️ Gitpod Examples

* [Selenium Test](./gitpod/src/test/java/com/saucedemo/selenium/SeleniumTest.java)
* [Appium Test](./gitpod/src/test/java/com/saucedemo/selenium/AppiumTest.java)

## ⚙️Setup/Prerequisites

* git
* JDK (11 or later)
* Maven
* IntelliJ, VS Code, or another IDE
* Sauce Labs account, with Username and Access Key

### Import the Project

1. Create a directory on your machine.

2. Clone this repository into the said directory.
    ```
    $ git clone https://github.com/saucelabs-training/demo-java.git
    ```

3. Import the project into your IDE. The project is a collection of Java modules, so be sure to
   enable the module you're interested in within the IDE (to allow Maven to recognize it properly).

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
   > To set an environment variable permanently in MacOS, you must set it within your chosen shell's
   profile config.

   **Windows:**
   ```
   > set SAUCE_USERNAME="username"
   > set SAUCE_ACCESS_KEY="accessKey"
   ```
   > To set an environment variable permanently in Windows, you must set it within System
   Properties.

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

1. Go to the chosen module where you'd like to do your testing (these examples will use
   `best-practice`)
    ```
    $ cd best-practice
    ```
2. Run the following command to update any package dependencies:
    ```
    $ mvn dependency:resolve
    ```
2. Then run the following command to compile your test code:
    ```
    $ mvn test-compile
    ```
3. Finally, run the following test to see if you've properly configured the test environment:
    ```
    $ mvn test -Dtest=DesktopTests
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
> uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. These
> scripts are
> provided for educational and demonstration purposes only and should not be used in production.
> Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by
> the Technical Services team at Sauce Labs.
>
> Some examples in this repository require an account tier beyond the free trial. Please contact
> the [Sauce Labs Sales Team](https://saucelabs.com/contact) for support and information.
