# Using Test App Upgrades/Mid-Session App Installations
This folder contains example for using Test App Upgrades/Mid-Session App Installations on Android real devices.   
Please read the documents about this feature [here](https://docs.saucelabs.com/mobile-apps/automated-testing/appium/test-app-upgrades/)

## Important information
### Environment variables for Sauce Labs
The examples in this repository use environment variables, make sure you've added the following

    # For Sauce Labs Emulators/Simulators/Real devices
    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******

### Demo app(s)
The demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/my-demo-app-android/releases).    
The two app versions that are used in this example can be downloaded from here: [Version 1.0.17](https://github.com/saucelabs/my-demo-app-android/releases/tag/1.0.17) and [Version 1.0.14](https://github.com/saucelabs/my-demo-app-android/releases/download/1.0.14/mda-1.0.14-17.apk)

### Upload apps to Sauce Storage
You need to upload the apps to the Sauce Storage. Please check [here](https://docs.saucelabs.com/mobile-apps/app-storage/) how to do it.    
**If you don't do that then the scripts can't find the apps!**

## Run tests on Sauce Labs Android real devices
You can run the Android test on Sauce platform with:

    // If using the US DC
    mvn clean test -Dtest=MidSessionAppInstallsAndroidTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=MidSessionAppInstallsAndroidTest -Dregion=eu
    
The tests will be executed on any available Samsung device, OS 13.

> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command