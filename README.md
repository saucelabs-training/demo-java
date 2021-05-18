# Java Demonstration Scripts

Welcome to Java Demo Scripts designed by Solution Architects to provide examples of how to use Sauce Labs technologies. This repository contains
everything that you need to get started with web, mobile, visual, functional and all other types of automation using Java.

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/564ddfb012db40048781b7b6c954d099)](https://app.codacy.com/gh/saucelabs-training/demo-java?utm_source=github.com&utm_medium=referral&utm_content=saucelabs-training/demo-java&utm_campaign=Badge_Grade_Dashboard)
![Front-end performance](https://github.com/saucelabs-training/demo-java/workflows/Front-end%20performance/badge.svg)
![Visual E2E Tests](https://github.com/saucelabs-training/demo-java/workflows/Visual%20E2E%20Tests/badge.svg)
![Real Devices Web Tests](https://github.com/saucelabs-training/demo-java/workflows/Real%20Devices%20Web%20Tests/badge.svg)
![Desktop Tests](https://github.com/saucelabs-training/demo-java/workflows/Desktop%20Tests/badge.svg)
[![TestNg Tests](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml/badge.svg)](https://github.com/saucelabs-training/demo-java/actions/workflows/testng.yml)
![RDC iOS Native App](https://github.com/saucelabs-training/demo-java/workflows/RDC%20iOS%20Native%20App/badge.svg)
![RDC Android Native App](https://github.com/saucelabs-training/demo-java/workflows/RDC%20Android%20Native%20App/badge.svg)

## 🥇Most Popular
*  [Web automation best practices framework with multiple testing strategies. Crafted by industry experts with decades of experience.](/best-practice/src/test/java/com/saucedemo/tests/)
*  [Quick start test, Junit 4](./selenium-junit4-examples/src/test/java/com/saucedemo/JUnit4Test.java)
*  [Quick start test, TestNg](./selenium-testng-examples/src/test/java/com/saucedemo/TestNgTest.java)
*  [W3C examples using Sauce Labs](https://github.com/saucelabs-training/demo-java/blob/master/w3c-examples)
*  [iOS real device, native app, Junit4](./appium-examples/src/test/java/com/realdevice/unifiedplatform/IOSNativeAppTest.java)
*  [Front-end performance testing](./selenium-junit4-examples/src/test/java/com/saucedemo/PerformanceExampleTests.java)
*  [Visual E2E test](./selenium-junit4-examples/src/test/java/com/saucedemo/SimpleVisualE2ETest.java)
*  [Sauce Connect usage](./selenium-junit4-examples/src/test/java/com/saucedemo/SauceConnectTest.java)

## 🖥Web automation

* All Examples
  * [JUnit 5 web test](/selenium-examples/src/test/java/com/saucedemo/JUnit5W3CChromeTest.java)
  * [JUnit 4,Cucumber web test](/java8/selenium3.junit4.examples.cucumber/src/test/resources/AddItem.feature)
  * [Front-end performance testing](./selenium-junit4-examples/src/test/java/com/saucedemo/PerformanceExampleTests.java)
  * [Visual tests on multiple platforms and resolutions](/best-practice/src/test/java/com/saucedemo/tests/VisualCrossPlatformTests.java)
  * [Windows authentication](./selenium-junit4-examples/src/test/java/com/saucedemo/WindowsAuthentication.java)
  * [JUnit 3 Sauce Status Updates](./selenium-examples/src/test/java/com/saucedemo/JUnit3UpdateSauceStatusTest.java)
  * [Single Browser in Parallel w/ TestNG](./selenium-testng-examples/src/test/java/com/saucedemo/ParallelSingleBrowserTest.java)
  * [Cross Browser/Platform in Parallel w/ TestNG](./selenium-testng-examples/src/test/java/com/saucedemo/CrossBrowserPlatformTest.java)

* Automation Best Practices
  * [Automation best practices with multiple testing strategies](/best-practice/src/test/java/com/saucedemo/tests/)
  * [TestNg Sample Framework](/selenium-testng-best-practice/src/)

## 📱Mobile automation
[📚 Mobile Testing Training Tutorials](./TRAINING.md)

* All Examples
  * Real Devices
    * [iOS native app, Junit4](./appium-examples/src/test/java/com/realdevice/unifiedplatform/IOSNativeAppTest.java)
    * [Android native app, Junit4](./appium-examples/src/test/java/com/realdevice/unifiedplatform/AndroidNativeAppTest.java)
    * [Set test status on mobile](./appium-examples/src/test/java/com/realdevice/unifiedplatform/AndroidNativeAppTest.java)
    * [Upload app to Sauce Storage](./appium-examples/src/test/java/com/realdevice/unifiedplatform/SauceStorage.sh)
    
  * Emulators and Simulators
    * [iOS web app, Junit4](./appium-examples/src/test/java/com/emusim)

* Automation Best Practices
  * [Emusim web testing](/best-practice/src/test/java/com/saucedemo/tests/EmusimWebAppTests.java)
  * [Real devices web testing](/best-practice/src/test/java/com/saucedemo/tests/RealDeviceWebTests.java)

* All Examples
  * [Android native app test](./appium-examples/src/test/java/com/realdevice/unifiedplatform/AndroidNativeAppTest.java)
  * [iOS native app test](./appium-examples/src/test/java/com/realdevice/unifiedplatform/IOSNativeAppTest.java)

## Prerequisites

* Install [Git](https://github.com/saucelabs-training/demo-java/blob/master/docs/prerequisites.md#install-git)
* Install [IntelliJ (or another IDE)](https://github.com/saucelabs-training/demo-java/blob/master/docs/prerequisites.md#install-intellij)
* Install [JDK](https://github.com/saucelabs-training/demo-java/blob/master/docs/prerequisites.md#install-the-jdk)
* Install [Maven](https://github.com/saucelabs-training/demo-java/blob/master/docs/prerequisites.md#install-maven)

> Select the button below to try this demo in [Gitpod](https://www.gitpod.io/)
>
> <a href="https://gitpod.io/#https://github.com/saucelabs-training/demo-java"><img src="https://github.com/saucelabs-training/demo-java/blob/master/docs/open-in-gitpod.png" title="Open in Gitpod"></a>
>
> [Click here](docs/gitpod_instructions.md) to see how to setup your Sauce Labs credentials in Gitpod

<br />

## Project Setup
* [Import the Project](#import-the-project)
* [Set Your Sauce Labs Credentials](#set-your-sauce-labs-credentials)
* [Run a Maven Test](#run-a-maven-test)
 
<br />


#### Import the Project

1. Create a directory on your machine.
2. Clone this repository into said directory.
    ```
    $ git clone https://github.com/saucelabs-training/demo-java.git
    ```
2. Import the project into your IntelliJ (or IDE of your choice) as a **Maven Project**.
3. Click through the prompts, and confirm when it asks to **Import from Sources**
4. Choose the **demo-java** directory as the **root** directory of the project.

#### Set Your Sauce Labs Credentials
1. Copy your Sauce Labs **username** and **accessKey** in the [User Settings](https://app.saucelabs.com/user-settings) section of the [Sauce Labs Dashboard](https://app.saucelabs.com/dashboard/builds).
2. Open a Terminal window (command prompt for Windows) and set your Sauce Labs Environment variables:   
   ###### Mac OSX:
   ```
   $ export SAUCE_USERNAME="username"
   $ export SAUCE_ACCESS_KEY="accessKey"
   ```
   ###### Windows:
   ```
   > set SAUCE_USERNAME="username"
   > set SAUCE_ACCESS_KEY="accessKey"
   ```
   > To set an environment variables permanently in Windows, you must append it to the `PATH` variable.
   
   > Go to **Control Panel > System > Windows version > Advanced System Settings > Environment Variables > System Variables > Edit > New**
   
   > Then set the "Name" and "Value" for each variable
   
9. Test the environment variables
    ###### Mac OSX:
    ```
    $ echo $SAUCE_USERNAME
    $ echo $SAUCE_ACCESS_KEY
    ```
    > ***WARNING FOR UNIX USERS!***:
    > If you have problems setting your environment variables, run the following commands in your terminal:
    ```
    $ launchctl setenv SAUCE_USERNAME $SAUCE_USERNAME
    $ launchctl setenv SAUCE_ACCESS_KEY $SAUCE_ACCESS_KEY
    ```
    ###### Windows:
    ```
    > echo %SAUCE_USERNAME%
    > echo %SAUCE_ACCESS_KEY%
    ```

<br />

#### Run a Maven Test

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
    $ mvn test -pl selenium-testng-examples -Dtest=TestNgTest 

    ```

    > If you wish to run a specific test/sub-module in this emusim_testng use the following command:
    >   ```
    >   # for running a specific test:
    >   mvn test -Dtest=testname
    > 
    >   # for running a specific sub-module
    >   mvn test -pl subproject/
    >   ```

> ###### Disclaimer:
>
> The code in these scripts is provided on an "AS-IS" basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. These scripts are provided for educational and demonstration purposes only, and should not be used in production. Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by the Technical Services team at Sauce Labs.
>
> Some examples in this repository, such as `appium-example`, `parallel-testing`, and `headless`, may require a different account tier beyond free trial. Please contact the [Sauce Labs Sales Team](https://saucelabs.com/contact) for support and information.
