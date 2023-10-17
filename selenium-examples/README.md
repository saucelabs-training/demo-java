# Selenium examples

This is the default project for all Selenium and Sauce Labs features

* Demo package for seeing Sauce Demo site tests
* Sauce Features package for demos of executing special Sauce Labs features
* Selenium Features package for demos of executing Selenium on Sauce Labs

It is also to demonstrate how to run tests in parallel with Selenium 4 and JUnit 5. See [pom.xml](pom.xml)

## Execute tests in parallel:
```bash
cd selenium-examples
mvn test -Dsurefire.parallel=20
```



