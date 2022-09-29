# Using biometric login on Sauce Labs
This folder contains examples for using Biometric login for:

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

### Demo app(s)
The demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/my-demo-app-rn/releases).
Be aware of the fact that and iOS simulator uses a different build then a iOS real device. So please check the file you
download.

> The advice is to download the files to an `apps` folder in the root of this folder.

Make sure that when you downloaded the files from the releases page, that you rename the apps to the following:

- `Android-MyDemoAppRN.{#.#.#}.build-{####}.apk` => `Android.MyDemoAppRN.apk`
- `iOS-Real-Device-MyRNDemoApp.{#.#.#}-{####}.ipa` => `iOS.MyDemoAppRN.ipa`
- `iOS-Simulator-MyRNDemoApp.{#.#.#}-{####}.zip` => `iOS.MyDemoAppRN.zip`

**If you don't do that then the scripts can't find the apps!**

### Upload apps to Sauce Storage
If you want to use Android emulators, Android real devices, iOS simulators or iOS real devices in the Sauce Labs platform, you need to upload 
the apps to the Sauce Storage.

#### Manual upload
Execute the following steps to manually upload the apps:
- Login to the Sauce Labs platform
- Go to **LIVE** > **Mobile App**
- Click on **App Upload** and OR select the folder, OR drag the apps to the screen to upload them

#### Automated upload
You can find a script to upload them to, OR the US, OR EU DC in [this](../../helpers/push_apps_to_storage.sh)-file. You can push the files to the
storage by doing the following from the folder `appium-app-examples`:

    cd src/test/java/com/helpers/
    push_apps_to_storage.sh

### iOS
Using TouchID or FaceID for iOS simulators is pretty straightforward, you **DON'T** need to add an extra capability to 
your capabilities, you can just enable it during runtime, please check [this](BiometricLoginIosSimTest.java#142) to 
see how to do that.

For running test on the Sauce Labs Real Device Cloud you need to add `allowTouchIdEnroll: true` to your capabilities,
please check [this](BiometricLoginIosRDCTest.java#L96) to see how to do that.

### Android
Android is not that straightforward as iOS when it comes to Emulators. There is no specific capability you can use to 
enable fingerprint support. You need to first enable it manually or with Appium to enable this. I used 
[this article](https://dev.to/gromanas/how-to-automate-biometrics-android-edition-2c7c) written by 
[Georgios Romanas](https://github.com/gromanas) to get the flow.

The challenge with Android emulators was that there is a different flow for almost each version.
In the example we coverd Android 10 emualtor.
The method `enableBiometricLogin()` in [this](AndroidSettings.java)-file will do all the magic for you

For running test on the Sauce Labs Real Device Cloud you need to add `allowTouchIdEnroll: true` to your capabilities,
please check [this](BiometricLoginAndroidRDCTest.java#L81) to see how to do that.

## Run tests on Sauce Labs Android real devices
If you want to run the tests on Sauce Labs Android Real Devices then you can run the Android test with

    // If using the US DC
     mvn clean test -Dtest=BiometricLoginAndroidRDCTest -Dregion=us
    
    // If using the EU DC
     mvn clean test -Dtest=BiometricLoginAndroidRDCTest -Dregion=eu
    
The tests, which can be found [here](BiometricLoginAndroidRDCTest.java), will be executed on:     
- Samsung Galaxy S(7|8|9|10|20|21).* ,Android 12
          
> Both devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular expression.
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

## Run tests on Sauce Labs Android Emulators
If you want to run the tests on Sauce Labs Android emulators then you can run the Android test with

    // If using the US DC
     mvn clean test -Dtest=BiometricLoginAndroidEmuTest -Dregion=us
    
    // If using the EU DC
     mvn clean test -Dtest=BiometricLoginAndroidEmuTest -Dregion=eu

The tests, which can be found [here](BiometricLoginAndroidEmuTest.java), will be executed on:

- Android GoogleAPI Emulator ,Android 10
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

> As mentioned in [Important information > Android](#android), there is no easy way to enable biometrics on Android
> emulators. That's why this manual process has been scripted with Appium.

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on iOS Sauce Labs Real Devices then you can run the iOS test with

    // If using the US DC
    mvn clean install -Dtest=BiometricLoginIosRDCTest -Dregion=us
    
    // If using the EU DC
    mvn clean install -Dtest=BiometricLoginIosRDCTest -Dregion=eu

The tests, which can be found [here](BiometricLoginIosRDCTest.java), will be executed on:

- iPhone ([6-8]|SE).* => supports Touch ID
- iPhone (11|12|13|X.*).* => supports Face ID

> Both devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular
expression.
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

## Run tests on Sauce Labs iOS Simulators
If you want to run the tests on Sauce Labs iOS simulators then you can run the Android test with

    // If using the US DC
    mvn clean install -Dtest=BiometricLoginIosSimTest -Dregion=us
    
    // If using the EU DC
    mvn clean install -Dtest=BiometricLoginIosSimTest -Dregion=eu

The tests, which can be found [here](BiometricLoginIosSimTest.java), will be executed on:

- iPhone 8 Plus Simulator => has Touch ID
- iPhone 12 Simulator => has Face ID
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

## Special Thanks
Huge thanks to [Wim Selles](https://github.com/wswebcreation). He develops and maintains the [Sauce Labs demo-js repository](https://github.com/saucelabs-training/demo-js). 
The js repo inspired the examples in this repository. Thanks my friend.
