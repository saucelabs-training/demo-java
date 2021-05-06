# Using biometric login on Sauce Labs
This folder contains examples for using Biometric login on real devices for:
-   [iOS real devices on the new Sauce Labs UI](#run-tests-on-sauce-labs-ios-real-devices)
-   [iOS simulators on the Sauce Labs Simulator Cloud](#run-tests-on-sauce-labs-iOS-simulators)
-   [Android emulators on the Sauce Labs Emulator Cloud](#run-tests-on-sauce-labs-android-emulators)

> Note: Android real devices are currently not supported

## Important information
### Environment variables for Sauce Labs
The examples in this repository use environment variables, make sure you've added the following

    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******
    
### Demo app(s)
The demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/sample-app-mobile/releases).
Be aware of the fact that iOS simulator uses a different build than a real iOS device. So please check the file you download.

> The advice is to download the files to an `apps` folder in the root of this folder.

### iOS
Using TouchID or FaceID for iOS simulators is pretty straightforward, you **don't** need to add an extra capability to your capabilities,
you can just enable it during runtime, please check [this](./BiometricLoginIosTest.java) to see how to do that.

On the real iOS devices you need to add `allowTouchIdEnroll: true` to your capabilities.
See [this](./BiometricLoginIosTest.java) where rdc = true   

### Android
Android is not that straightforward as iOS, there is no specific capability you can use to enable fingerprint support. 
I used [this article](https://dev.to/gromanas/how-to-automate-biometrics-android-edition-2c7c) written by [Georgios Romanas](https://github.com/gromanas)
to get the flow.

The challenge with Android was that there is a different flow for almost each version. Take for example Android 7, 
that version doesn't support to automatically set a pin, you need to walk through a complete flow to enable this. 
There is also a small different in the fingerprint wizard between Android 9 and 10. 
In this example, I covered the flow for Android 8, see [this](./AndroidSettings.java)-file. 
The method `enableBiometricLogin()` will do all the magic for you. 

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on Sauce Labs real devices then you can run the iOS test with

    // If using the US DC
    mvn clean install -Dtest=BiometricLoginIosTest -Dregion=us -Drdc=true
    
    // If using the EU DC
    mvn clean install -Dtest=BiometricLoginIosTest -Dregion=eu -Drdc=true
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `appium-examples` when you execute this command

## Run tests on Sauce Labs iOS simulators
If you want to run the tests on Sauce Labs iOS simulators then you can run the iOS test with

    // If using the US DC
    mvn clean test -Dtest=BiometricLoginIosTest -Dregion=us -Drdc=false
    
    // If using the EU DC
    mvn clean test -Dtest=BiometricLoginIosTest -Dregion=eu -Drdc=false
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `appium-examples` when you execute this command

## Run tests on Sauce Labs Android emulators
If you want to run the tests on Sauce Labs Android emulators then you can run the Android test with

    // If using the US DC
     mvn clean test -Dtest=BiometricLoginAndroidTest -Dregion=us
    
    // If using the EU DC
     mvn clean test -Dtest=BiometricLoginAndroidTest -Dregion=eu
    
The tests will be executed on a Android 8

> NOTE: Make sure you are in the folder `appium-examples` when you execute this command
