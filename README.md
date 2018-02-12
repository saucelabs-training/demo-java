## Java-CucumberJVM-TestNG

>This code is presented as an example only, since your tests and testing environments may require specialized scripting. This information should be taken only as an
>illustration of how one would set up tests with Sauce Labs, and any modifications will not be supported. For questions regarding Sauce Labs integration, please see
>our documentation at https://wiki.saucelabs.com/.

### Environment Setup

1. Global Dependencies
    * Install Maven
    	https://maven.apache.org/install.html
    * Or Install Maven with Homebrew
    	http://brew.sh/
    ```
    $ brew install maven
    ```
2. TestObject
    * Add your TestObject API Key and Appium version you would like to run against
    ```
    $ export TESTOBJECT_API_KEY=<your project's api key>
    $ export APPIUM_URL=<appropriate RDC Appium endpoint URL>
    ```

### Running Tests
	* Note: Makefile needs to be updated if you wish to run on more than 2 devices at once.
to run: `make run_all_in_parallel`

### Advice/Troubleshooting
1. It may be useful to use a Java IDE such as IntelliJ or Eclipse to help troubleshoot potential issues.

### Resources
##### [TestObject REST API Documentation](https://api.testobject.com/)

##### [Sauce Labs Documentation](https://wiki.saucelabs.com/)

##### [TestNG Documentation](http://testng.org/doc/documentation-main.html)

##### [Java Documentation](https://docs.oracle.com/javase/7/docs/api/)

##### [Stack Overflow](http://stackoverflow.com/)
* A great resource to search for issues not explicitly covered by documentation.









# Changes to be made

```
	export TESTOBJECT_API_KEY=your_api_key
	export SAUCE_ACCESS_KEY=your_access_key
```


