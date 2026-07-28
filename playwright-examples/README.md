# Playwright Examples

Tests connect to Sauce Labs through Playwright's native WebSocket endpoint, so any of
Playwright's engines - Chromium, Firefox or WebKit - works the same way.

[Setup Instructions](https://github.com/saucelabs-training/demo-java/blob/main/README.md#%EF%B8%8Fsetupprerequisites)
and
[Contribution Information](https://github.com/saucelabs-training/demo-java/blob/main/README.md#contributing)
can be found on
the [Main README](https://github.com/saucelabs-training/demo-java/blob/main/README.md).

## Executing Examples

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

   See passing tests
   on [GitHub Actions](https://github.com/saucelabs-training/demo-java/actions/workflows/playwright-examples.yml)

## Configurations

### Browser

This code allows toggling which browser engine the tests will run on: `chromium` (default),
`firefox`, or `webkit`.

   ```
   $ mvn clean test -Dsauce.browser.name=firefox
   ```

### Session grouping / parallelism

By default, all the test methods in a test class share a single Sauce session (one Sauce job per
test class), and different test classes run in parallel with each other. A session that sees a
test failure is closed and reported immediately rather than reused by the next test.

To give every test method its own independent Sauce session instead, switch JUnit5's own
method-execution mode to `concurrent`:

   ```
   $ mvn clean test -Djunit.parallel.mode.default=concurrent
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
