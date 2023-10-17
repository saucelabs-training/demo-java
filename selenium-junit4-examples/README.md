# Selenium JUnit 4 examples

This project is to demonstrate how to run JUnit 4 tests on Sauce Labs including:
* Setting correct options
* Sending test results to Sauce
* Running in Parallel with Maven surefire. See [pom.xml](pom.xml)

## Execute tests in parallel:
```bash
cd selenium-examples
mvn test -Dsurefire.parallel=20
```
