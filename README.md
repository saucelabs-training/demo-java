![alt](https://saucelabs.com/images/sauce-labs-logo.png)

## Java-TestNg-Selenium

>This code is presented as an example only, since your tests and testing environments may require specialized scripting. This information should be taken only as an
>illustration of how one would set up tests with Sauce Labs, and any modifications will not be supported. For questions regarding Sauce Labs integration, please see 
>our documentation at https://wiki.saucelabs.com/.

### Environment Setup

1. Global Dependencies
    * [Install Maven](https://maven.apache.org/install.html)
    * Or Install Maven with [Homebrew](http://brew.sh/)
    ```
    $ brew install maven
    ```
2. Sauce Credentials
    * In the terminal export your Sauce Labs Credentials as environmental variables:
    ```
    $ export SAUCE_USERNAME=<your Sauce Labs username>
	$ export SAUCE_ACCESS_KEY=<your Sauce Labs access key>
    ```
3. Project Dependencies
	* Check that Packages are available
	```
	$ cd Java-TestNg-Selenium
	$ mvn test-compile
	```
	* You may also want to run the command below to check for outdated dependencies. Please be sure to verify and review updates before editing your pom.xml file. The updated packages may or may not be compatible with your code.
	```
	$ mvn versions:display-dependency-updates
	```
### Running Tests

Tests in Parallel:
	```
	$ mvn test
	```

[Sauce Labs Dashboard](https://saucelabs.com/beta/dashboard/)

### Advice/Troubleshooting
1. It may be useful to use a Java IDE such as IntelliJ or Eclipse to help troubleshoot potential issues. 

### Resources
##### [Sauce Labs Documentation](https://wiki.saucelabs.com/)

##### [SeleniumHQ Documentation](http://www.seleniumhq.org/docs/)

##### [TestNg Documentation](http://testng.org/javadocs/index.html)

##### [Java Documentation](https://docs.oracle.com/javase/7/docs/api/)

##### Stack Overflow:
* [Related Stack Overflow Threads](http://stackoverflow.com/questions/27355003/advise-on-hierarchy-for-element-locators-in-selenium-webdriver)
