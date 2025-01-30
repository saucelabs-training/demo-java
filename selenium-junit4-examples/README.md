# Selenium JUnit 4 examples

This project demonstrates Sauce Labs Testing with Selenium and JUnit 4, including:
* Setting valid options
* Sending test results to Sauce Labs
* Running in Parallel with Maven surefire. See [pom.xml](pom.xml)

## Execute tests in parallel:
```bash
  cd selenium-examples
  mvn test -Dsurefire.parallel=20
```
