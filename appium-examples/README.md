# Appium examples
This folder contains Appium examples

## Examples
- [Using biometric login on Sauce Labs](./src/test/java/biometric_login)
- [Using image injection on Sauce Labs](./src/test/java/image_injection)
- [Android native app test](./src/test/java/com/realdevice/unifiedplatform/AndroidNativeAppTest.java)
- [Emusim iOS web app test](./src/test/java/com/emusim/IOSWebAppExample.java)

## How to run tests

### 🤖Android native app test
```bash
cd appium-examples
mvn test -Dtest=AndroidNativeAppTest
```

### 🍎iOS native app test
```bash
cd appium-examples
mvn test -Dtest=IOSNativeAppTest
```
### iOS web app test on Emusim
```bash
cd appium-examples
mvn test -Dtest=IOSWebAppExample
```
