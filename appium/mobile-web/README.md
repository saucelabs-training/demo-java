# Testing a Mobile Website

If you need to test your website on mobile devices with Appium, you have 5 choices.
* Chrome on Android Device on Sauce Labs Real Device Cloud
* Safari on iOS Device on Sauce Labs Real Device Cloud
* Chrome on Emulator in Sauce Labs Virtual Device Cloud
* Safari on Simulator in Sauce Labs Virtual Device Cloud
* [Chrome Mobile Emulation](../../selenium-junit5-examples/README.md#chrome-mobile-emulation)

[Setup Instructions](https://github.com/saucelabs-training/demo-java/blob/main/README.md#%EF%B8%8Fsetupprerequisites)
and
[Contribution Information](https://github.com/saucelabs-training/demo-java/blob/main/README.md#contributing)
can be found on the [Main README](https://github.com/saucelabs-training/demo-java/blob/main/README.md).

## Executing Examples
The Website being tested is [Swag Labs](https://www.saucedemo.com/).

1. After cloning this repo from instructions, change to this subdirectory:
    ```
   $ cd appium/mobile-web
   ```

2. Run the following command to update any package dependencies:
    ```
    $ mvn dependency:resolve
    ```
3. Then run the following command to compile your test code:
    ```
    $ mvn test-compile
    ```
4. Finally, run the following test to see if you've properly configured the test environment:
    ```
    $ mvn clean test
    ```

   See passing tests on [GitHub Actions](https://github.com/saucelabs-training/demo-java/actions/workflows/appium-examples.yml)

## Configurations
This code allows toggling what cloud the tests will target.
The capabilities for these configurations can be copied from the
[Platform Configurator](https://app.saucelabs.com/platform-configurator), 
and can be viewed in the [Test Configurations](src/test/java/com/saucedemo/TestConfigurations.java) file.

### Chrome on Virtual Device Cloud (default)
Tests will execute on an Android Emulator:
   ```
   $ mvn clean test -Dsauce.browser=chrome -Dsauce.cloud=vdc 
   ```

### Safari on Virtual Device Cloud
Tests will execute on an iOS Simulator:
   ```
   $ mvn clean test -Dsauce.browser=safari -Dsauce.cloud=vdc
   ```

### Chrome on Real Device Cloud
Tests will execute on a real Android device:
   ```
   $ mvn clean test -Dsauce.browser=chrome -Dsauce.cloud=rdc
   ```

### Safari on Real Device Cloud
Tests will execute on a real iOS device:
   ```
   $ mvn clean test -Dsauce.browser=safari -Dsauce.cloud=rdc
   ```

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
