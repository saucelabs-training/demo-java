# Appium examples
This folder contains Appium examples

## Examples
* [Using biometric login on Sauce Labs](./src/test/java/com/emusim/biometric_login)
* [Using image injection on Sauce Labs](./src/test/java/com/realdevice/image_injection)
* [Android native app test](./src/test/java/com/realdevice/AndroidNativeAppTest.java)
* [EmuSim iOS web app test](./src/test/java/com/emusim/IOSWebAppExample.java)

## How to run tests

### 🤖Android native app test
```bash
cd appium-examples
mvn test -Dtest=AndroidNativeAppTest
```

**Dynamic app name**

```bash
cd appium-examples
export ANDROID_APP=YOUR APP NAME HERE
mvn test -Dtest=AndroidNativeAppTest
```

### 🍎iOS native app test
```bash
cd appium-examples
mvn test -Dtest=IOSNativeAppTest
```
### iOS web app test on EmuSim
```bash
cd appium-examples
mvn test -Dtest=IOSWebAppExample
```

## Regex

Use [dynamic device allocation](https://wiki.saucelabs.com/display/DOCS/Dynamic+Device+Allocation) and [Regex](https://www.regular-expressions.info/lookaround.html) to find the devices that you want. Here are some examples:

* Find any iPhone
  
```java
capabilities.setCapability("deviceName", "iPhone.*");
```

* Find any iPhone that isn't 5 or 5S

```java
capabilities.setCapability("deviceName", "^(iPhone.*)(?!5|5S)$");
```

Please see [Regex](https://www.regular-expressions.info/lookaround.html) tutorials for more examples.
