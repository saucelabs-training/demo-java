# How to use Allowlisting App on Sauce Labs Private devices

Allow-listing your App on your Sauce Labs Private Devices means -
Your Apps will not be removed from your private device in the end of a live or automated session.   
You can read more about
that [here](https://docs.saucelabs.com/mobile-apps/real-device-cleaning/#private-devices)
If you want to allowlist your app, please contact your CSM with your private device id and your app
```bundle id``` for iOS or the app ```package name``` for Android.     
This is an example, how you can run automated tests on your allowlisting app.   
For a demo purpose, my allowlisting app is the iOS Settings app ( Bundle id:
`````"com.apple.Preferences"`````).   
For running your business flow on iOS allowlisting app you need to use the
capability [appium:bundleId](https://docs.saucelabs.com/dev/test-configuration-options/#appiumbundleid)   
For running your business flow on Android allowlisting app you need to use the
capabilities [appium:appPackage](https://docs.saucelabs.com/dev/test-configuration-options/#appiumapppackage)
and [appium:appActivity](https://docs.saucelabs.com/dev/test-configuration-options/#appiumappactivity).

## Important information

### Environment variables for Sauce Labs

The examples in this repository use environment variables, make sure you've added the following

    # For Sauce Labs Emulators/Simulators/Real devices
    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******

## Run tests on Sauce Labs iOS real devices

If you want to run the tests on Sauce Labs Android Real Devices then you can run the Android test
with

    // If using the US DC
     mvn clean test -Dtest=IOSAllowlistTest -Dregion=us
    
    // If using the EU DC
     mvn clean test -Dtest=IOSAllowlistTest -Dregion=eu

The tests, which can be found [here](IOSAllowlistTest.java), will be executed on:

- Any available iPhone device with OS 14

> The devices use *dynamic* allocation, meaning they will try to find an available device that
> matches a regular expression.

> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command
