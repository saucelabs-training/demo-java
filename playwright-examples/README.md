# Playwright Examples

There is only one way to execute Playwright on Sauce Labs with Java.
It works with any production version of Chrome or Edge.

[Setup Instructions](https://github.com/saucelabs-training/demo-java/blob/main/README.md#%EF%B8%8Fsetupprerequisites)
and
[Contribution Information](https://github.com/saucelabs-training/demo-java/blob/main/README.md#contributing)
can be found on the [Main README](https://github.com/saucelabs-training/demo-java/blob/main/README.md).

## Executing Examples
The Android app being tested is found in the [My Demo App Android Repository](https://github.com/saucelabs/my-demo-app-android)

1. After cloning this repo from instructions, change to this subdirectory:
    ```
   $ cd playwright-examples
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

   See passing tests on [GitHub Actions](https://github.com/saucelabs-training/demo-java/actions/workflows/playwright-examples.yml)

## Configurations
This code allows toggling which browser the tests will run on.

### Chrome (default)
Tests will execute on the latest version of Chrome:
   ```
   $ mvn clean test -Dsauce.browser=Chrome
   ```

### Microsoft Edge
Tests will execute on the latest version of Edge:
   ```
   $ mvn clean test -Dsauce.browser=MicrosoftEdge
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