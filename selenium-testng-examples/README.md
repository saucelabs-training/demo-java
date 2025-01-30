# Selenium TestNG Examples
This folder contains Selenium examples specific to TestNG

## Examples

- [Single Browser in Parallel w/ TestNG](./src/test/java/com/saucedemo/selenium/testng/ParallelSingleBrowserTest.java)
- [Cross Browser/Platform in Parallel w/ TestNG](./src/test/java/com/saucedemo/selenium/testng/CrossBrowserPlatformTest.java)

## How to run tests

### Single Browser Test in Parallel
```bash
cd selenium-testng-examples
mvn test -Dtest=com.saucedemo.ParallelSingleBrowserTest
```

### Cross Browser/Platform Test in Parallel
```bash
cd selenium-testng-examples
mvn test -Dtest=com.saucedemo.CrossBrowserPlatformTest
```