# Selenium examples

This is the default project for all Selenium and Sauce Labs features

There are 3 packages:

* Demo package for seeing Sauce Demo site tests
* Sauce Labs Features package for demos of functionality exclusive to Sauce Labs
* Selenium Features package for demos of Selenium features on Sauce Labs

It is also to demonstrate how to run tests in parallel with Selenium 4 and JUnit 5. See [pom.xml](pom.xml)

## Execution options:

First, ensure you are in `selenium-examples` directory

```bash
cd selenium-examples
```

1. Run everything with default parallelization:
    ```bash
    mvn clean test
    ```
2. Run only demo tests:
    ```bash
    mvn clean test -Dtest='com.saucedemo.selenium.demo.*Test'
    ```
3. Change parallelization
    ```bash
    mvn test -Dsurefire.parallel=20
    ```
