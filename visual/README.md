# visual-testing-worker-tests

## Table of Contents

- [About](#about)
- [Getting Started](#getting_started)
- [Usage](#usage)

## About <a name = "about"></a>

These are the webdriver tests using java.

## Getting Started <a name = "getting_started"></a>

These instructions will get you a copy of the project up and running on your local machine for development and 
testing purposes.

### Using docker to run tests
Building
```
docker build -t java-tests .
```
Running
```
docker run -it -e SAUCE_USERNAME=$SAUCE_USERNAME -e SAUCE_ACCESS_KEY=$SAUCE_ACCESS_KEY -e SCREENER_API_KEY=$SCREENER_API_KEY -e SELENIUM_PROTOCOL=http -e SELENIUM_HOST=staging-hub.screener.io -e SELENIUM_PORT=80 java-test
```

### Using VSCode to run tests

* Install maven
  ```bash
  brew install maven
  ```
* Install VSCode Extensions
  * Maven for Java
  * Java Extension pack

#### Usage <a name = "usage"></a>

From the folder with the pom.xml file run:
```
SELENIUM_PROTOCOL="http" SELENIUM_HOST="staging-hub.screener.io" SELENIUM_PORT="80" SAUCE_USERNAME=<YOUR_SAUCE_USERNAME> SAUCE_ACCESS_KEY=<YOUR_SAUCE_ACCESS_KEY> SCREENER_API_KEY=<YOUR_SCREENER_API_KEY> mvn clean test
```
