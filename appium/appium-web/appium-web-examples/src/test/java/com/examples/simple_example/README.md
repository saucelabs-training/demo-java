# Java Web App Simple Example on Sauce Labs
This folder is a very simple example to get you started with your appium testing on Web App.
This folder contains examples for:

- [Android Real Devices in the Sauce Labs Cloud](#run-tests-on-sauce-labs-android-real-devices)
- [Android Emulators in the Sauce Labs Cloud](#run-tests-on-sauce-labs-android-emulators)
- [iOS Real Devices in the Sauce Labs Cloud](#run-tests-on-sauce-labs-ios-real-devices)
- [iOS Simulators in the Sauce Labs Cloud](#run-tests-on-sauce-labs-ios-simulators)

## Important information
### Environment variables for Sauce Labs
The examples in this repository use environment variables, make sure you've added the following

    # For Sauce Labs Emulators/Simulators/Real devices
    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******


## Run tests on Sauce Labs Android real devices
If you want to run the tests on Sauce Labs Android Real Devices then you can run the Android test with

    // If using the US DC
     mvn clean test -Dtest=AndroidWebAppTest -Dregion=us
    
    // If using the EU DC
     mvn clean test -Dtest=AndroidWebAppTest -Dregion=eu
    
The tests, which can be found [here](AndroidWebAppTest.java), will be executed on:     
- Any available Samsung device with OS Android 12
          
> The devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular expression.
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

## Run tests on Sauce Labs Android Emulators
If you want to run the tests on Sauce Labs Android emulators then you can run the Android test with

    // If using the US DC
     mvn clean test -Dtest=AndroidWebAppTest -Dregion=us -Drdc=false
    
    // If using the EU DC
     mvn clean test -Dtest=AndroidWebAppTest -Dregion=eu -Drdc=false

The tests, which can be found [here](AndroidWebAppTest.java), will be executed on:

- Android GoogleAPI Emulator with OS Android 12
> NOTE: Make sure you are in the folder `appium-web-examples` when you execute this command

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on iOS Sauce Labs Real Devices then you can run the iOS test with

    // If using the US DC
    mvn clean install -Dtest=IOSWebAppTest -Dregion=us
    
    // If using the EU DC
    mvn clean install -Dtest=IOSWebAppTest -Dregion=eu

The tests, which can be found [here](IOSWebAppTest.java), will be executed on:

- Any available iPhone with OS 14

> The devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular
expression.
> NOTE: Make sure you are in the folder `appium-web-examples` when you execute this command

## Run tests on Sauce Labs iOS Simulators
If you want to run the tests on Sauce Labs iOS simulators then you can run the Android test with

    // If using the US DC
    mvn clean install -Dtest=IOSWebAppTest -Dregion=us -Drdc=false
    
    // If using the EU DC
    mvn clean install -Dtest=IOSWebAppTest -Dregion=eu -Drdc=false

The tests, which can be found [here](IOSWebAppTest.java), will be executed on:

- iPhone 11 Simulator with OS 14
> NOTE: Make sure you are in the folder `appium-web-examples` when you execute this command

