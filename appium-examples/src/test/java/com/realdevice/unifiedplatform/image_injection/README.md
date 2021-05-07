# Using image injection on Sauce Labs Real Devices
This folder contains examples for using image injection on real devices for:

- [Android real devices on the new Sauce Labs UI](#run-tests-on-sauce-labs-android-real-devices-in-the-new-sauce-labs-ui)
- [iOS real devices on the new Sauce Labs UI](#run-tests-on-sauce-labs-ios-real-devices-in-the-new-sauce-labs-ui)

> NOTE: this feature is **NOT** supported for emulators and simulators!!

## Important information

### Demo app(s)
The demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/sample-app-mobile/releases).
Be aware of the fact that you need the build for the iOS real device. So please check the file you download.

> The advice is to download the files to an `apps` folder in the root of this folder.

## Run tests on Sauce Labs Android real devices in the New Sauce Labs UI
If you want to run the tests on Sauce Labs real devices in the **New Sauce Labs UI** then you can run the Android test with

    // If using the US DC
    mvn clean test -Dtest=ImageInjectionAndroidTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=ImageInjectionAndroidTest -Dregion=eu
    
The tests will be executed on a Samsung Galaxy 10.

> NOTE: Make sure you are in the folder `appium-examples` when you execute this command

## Run tests on Sauce Labs iOS real devices in the New Sauce Labs UI
If you want to run the tests on Sauce Labs real devices in the **New Sauce Labs UI** then you can run the iOS test with

    // If using the US DC
    mvn clean test  -Dtest=ImageInjectionIosTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=ImageInjectionIosTest -Dregion=eu
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `appium-examples` when you execute this command
