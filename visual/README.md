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

### Expected Environment Variables
You'll need to set these three environment variables, whether on your system or as part of the command line:

```bash
"SAUCE_USERNAME"
"SAUCE_ACCESS_KEY"
"SCREENER_API_KEY"
```

### With Maven Directly
* Install maven
```bash
brew install maven
```

* From the folder with the pom.xml file run:
```bash
mvn clean test
```

### Using Docker Image
Building
```
docker build -t java-tests .
```
Running
```
docker run -it java-test
```
