# Using Biometric login on Sauce Labs
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

### Upload apps to Sauce Storage
-   If you want to use Android emulators, iOS simulators or iOS real devices in the New Sauce Labs UI you need to upload the apps to the Sauce Storage.
For more information on this step **for Real Devices** please visit: [Application Storage](https://wiki.saucelabs.com/display/DOCS/Application+Storage).

-   In the example for iOS real devices, for the app capability we use storage:filename=<app-name>.ipa. For more information on this step please visit: [Using Application Storage with Automated Test Builds](https://wiki.saucelabs.com/display/DOCSDEV/Application+Storage#ApplicationStorage-UsingApplicationStoragewithAutomatedTestBuilds) section of [Application Storage](https://wiki.saucelabs.com/display/DOCS/Application+Storage)

-   Change the value of appName parameter in SwagLabsTest.java for Android and iOS according to your app name.

-   **For Android emulators and iOS simulators**, You can find a script to upload the apps to, or the US, or EU DC in [this](./helpers)-folder. You can push the files to the 
  storage by doing the following from the root of this folder:
  

		cd helpers   
		./push_apps_to_storage.sh
      
  When you've done that you will see for example the following logs
  
      ➜  scripts git:(master) ✗ ./push_apps_to_storage.sh 
      {"username":"eyalyovel","filename":"sample-app-android.apk","size":24874172,"md5":"e46219548268d3e89ada443e1ed6e351","etag":"8b037c2ad1dc2b241e605ed97569d6dd"}
      {"username":"eyalyovel","filename":"sample-app-ios-sim.zip","size":8178727,"md5":"4c551e66213832ff982e302014917adb","etag":"23256688a3f6357ad4c1c8cd1ed72b3e"}
      
### iOS
Using TouchID or FaceID for iOS simulators is pretty straightforward, you **don't** need to add an extra capability to your capabilities,
you can just enable it during runtime, please check [this](./src/test/java/ios/tests/SwagLabsTest.java) to see how to do that.

On the real iOS devices you need to add `allowTouchIdEnroll: true` to your capabilities.
See [this](./src/test/java/ios/tests/SwagLabsTest.java) where rdc = true   

### Android
Android is not that straightforward as iOS, there is no specific capability you can use to enable fingerprint support. 
I used [this article](https://dev.to/gromanas/how-to-automate-biometrics-android-edition-2c7c) written by [Georgios Romanas](https://github.com/gromanas)
to get the flow.

The challenge with Android was that there is a different flow for almost each version. Take for example Android 7, 
that version doesn't support to automatically set a pin, you need to walk through a complete flow to enable this. 
There is also a small different in the fingerprint wizard between Android 9 and 10. 
In this example, I covered the flow for Android 8, see [this](./src/test/java/helpers/AndroidSettings.java)-file. 
The method `enableBiometricLogin()` will do all the magic for you. 

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on Sauce Labs real devices then you can run the iOS test with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_ios.xml -Dregion=us -Drdc=true
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_ios.xml -Dregion=eu -Drdc=true
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `biometric-login` when you execute this command

## Run tests on Sauce Labs iOS simulators
If you want to run the tests on Sauce Labs iOS simulators then you can run the iOS test with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_ios.xml -Dregion=us -Drdc=false
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_ios.xml -Dregion=eu -Drdc=false
    
The tests will be executed on a iPhone 8.
> NOTE: Make sure you are in the folder `biometric-login` when you execute this command

## Run tests on Sauce Labs Android emulators
If you want to run the tests on Sauce Labs Android emulators then you can run the Android test with

    // If using the US DC
    mvn clean install -DtestngXmlFile=testng_android.xml -Dregion=us
    
    // If using the EU DC
    mvn clean install -DtestngXmlFile=testng_android.xml -Dregion=eu
    
The tests will be executed on a Android 8

> NOTE: Make sure you are in the folder `biometric-login` when you execute this command
