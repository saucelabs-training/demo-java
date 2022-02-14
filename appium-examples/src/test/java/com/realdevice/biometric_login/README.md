# Using biometric login on Sauce Labs Real Devices
This folder contains examples for using Biometric login on real devices for:
-   [iOS Real Devices in the Sauce Labs Real Device Cloud](#run-tests-on-sauce-labs-ios-real-devices)
-   [Android Real Devices in the Sauce Labs Real Device Cloud](#run-tests-on-sauce-labs-android-real-devices)

## Important information
### Environment variables for Sauce Labs
The examples in this repository that can run on Sauce Labs use environment variables, make sure you've added the following

    # For Sauce Labs Emulators/Simulators/Real devices
    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******

### Demo app(s)
The demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/my-demo-app-rn/releases).
Be aware of the fact that and iOS simulator uses a different build then a iOS real device. So please check the file you
download.

> The advice is to download the files to an `apps` folder in the root of this folder.

Make sure that when you downloaded the files from the releases page, that you rename the apps to the following, see
also the names of the apps in the configurations files [here](test/configs):

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
You can find a script to upload them to, OR the US, OR EU DC in [this](scripts)-folder. You can push the files to the
storage by doing the following from the root of this folder:

    cd scripts
    ./push_apps_to_storage.sh

### iOS and Android
For running test on the Sauce Labs Real Device Cloud you need to add `allowTouchIdEnroll: true` to your capabilities

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on Sauce Labs real devices then you can run the iOS test with

    // If using the US DC
    mvn clean install -Dtest=BiometricLoginIosRDCTest -Dregion=us
    
    // If using the EU DC
    mvn clean install -Dtest=BiometricLoginIosRDCTest -Dregion=eu
    
The tests will be executed on:
- iPhone ([6-8]|SE).* => supports Touch ID
- iPhone (11|12|13|X.*).* => supports Face ID

> Both devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular
expression
> NOTE: Make sure you are in the folder `appium-examples` when you execute this command

## Run tests on Sauce Labs Android real devices
If you want to run the tests on Sauce Labs Android Real Devices then you can run the Android test with

    // If using the US DC
     mvn clean test -Dtest=BiometricLoginAndroidRDCTest -Dregion=us
    
    // If using the EU DC
     mvn clean test -Dtest=BiometricLoginAndroidRDCTest -Dregion=eu
    
The tests will be executed on:     
- Samsung Galaxy S(7|8|9|10|20|21).*
          
> Both devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular expression.
> NOTE: Make sure you are in the folder `appium-examples` when you execute this command
