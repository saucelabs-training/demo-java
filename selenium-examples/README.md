# Selenium examples

The primary function of this project is to demonstrate using Sauce Labs with JUnit 5 (The [demo](/src/test/java/com/saucedemo/selenium/demo)), including:
* Setting valid options
* Sending test results to Sauce Labs
* Running in Parallel with Maven surefire. See [pom.xml](pom.xml)

In addition, this project contains two additional packages:

* Sauce Labs Features package for demos of functionality exclusive to Sauce Labs
* Selenium Features package for demos of Selenium features on Sauce Labs

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
