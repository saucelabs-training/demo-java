# Java Demonstration Scripts

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/564ddfb012db40048781b7b6c954d099)](https://app.codacy.com/gh/saucelabs-training/demo-java?utm_source=github.com&utm_medium=referral&utm_content=saucelabs-training/demo-java&utm_campaign=Badge_Grade_Dashboard)
[![CircleCI](https://circleci.com/gh/saucelabs-training/demo-java/tree/master.svg?style=svg)](https://circleci.com/gh/saucelabs-training/demo-java/tree/master)

The demo scripts in [this repository](https://github.com/saucelabs-training/demo-java) allow you to run simple automated tests in order to validate your Java Selenium environment and your [saucelabs.com](https://app.saucelabs.com/login) account credentials.

> ###### Disclaimer:
>
> The code in these scripts is provided on an "AS-IS" basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. These scripts are provided for educational and demonstration purposes only, and should not be used in production. Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by the Technical Services team at Sauce Labs.
>
> Some examples in this repository, such as `appium-example`, `parallel-testing`, and `headless`, may require a different account tier beyond free trial. Please contact the [Sauce Labs Sales Team](https://saucelabs.com/contact) for support and information.


## Solution Outline
* [Tests that can help you quickly and easily get started with Sauce Labs](https://github.com/saucelabs-training/demo-java/blob/master/on-boarding-modules)
* [Advanced tests that use test automation best practices on Sauce Labs](https://github.com/saucelabs-training/demo-java/blob/master/parallel-testing)
* [W3C examples using Sauce Labs](https://github.com/saucelabs-training/demo-java/blob/master/w3c-examples)
* [Tests that use the Headless feature of Sauce Labs](https://github.com/saucelabs-training/demo-java/blob/master/headless)
* [Mobile Examples using Appium on Sauce Labs](https://github.com/saucelabs-training/demo-java/blob/master/appium-example)

### Web
* [JUnit 5 web test, simple](/java11/selenium3-junit5-examples/src/test/java/com/saucedemo/JUnit5W3CChromeTest.java)
* [JUnit 4,Cucumber web test, simple](/java8/selenium3.junit4.examples.cucumber/src/test/resources/AddItem.feature)

### Mobile
* [iOS real device, native app, Junit4, Unified Platform](/java11/appium-junit4-examples/src/test/java/com/realdevice.unifiedplatform/IOSNativeAppUnifiedPlatformTest.java)
* [iOS Simulator, Web app, Junit4](/java11/appium-junit4-examples/src/test/java/com/emusim/IOSNativeAppExample.java)

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
    $ mvn test -Dtest=Module2TestNGTest -pl on-boarding-modules/testng

    ```

    > If you wish to run a specific test/sub-module in this emusim_testng use the following command:
    >   ```
    >   # for running a specific test:
    >   mvn test -Dtest=testname
    > 
    >   # for running a specific sub-module
    >   mvn test -pl subproject/
    >   ```
