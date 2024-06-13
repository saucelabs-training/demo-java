# Using Network Throttling on Sauce Labs Real Devices
This folder contains examples for using Network Throttling on real devices for:

- [iOS real devices on the Sauce Labs Cloud](#run-tests-on-sauce-labs-ios-real-devices)
- [Android real devices on the Sauce Labs Cloud](#run-tests-on-sauce-labs-android-real-devices)

## Important information
### Environment variables for Sauce Labs
The examples in this repository use environment variables, make sure you've added the following

    # For Sauce Labs Emulators/Simulators/Real devices
    export SAUCE_USERNAME=********
    export SAUCE_ACCESS_KEY=*******

### Demo app(s)
The Android demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/my-demo-app-android/releases).   
The iOS demo app that has been used for all these tests can be found [here](https://github.com/saucelabs/my-demo-app-ios/releases).   

> The advice is to download the files to an `apps` folder in the root of this folder.

Make sure that when you downloaded the files from the releases page, that you rename the apps to the following:

- `mda-{#.#.#}.apk` => `SauceLabs-Demo-App.apk`

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

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on iOS Sauce Labs Real Devices then you can run the iOS test with

    // If using the US DC
    mvn clean test -Dtest=NetworkThrottlingIosRDCTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=NetworkThrottlingIosRDCTest -Dregion=eu

The tests, which can be found [here](NetworkThrottlingIosRDCTest.java), will be executed on:

- Any available iPhone or iPad

> The devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular
expression.
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command


## Run tests on Sauce Labs Android real devices
If you want to run the tests on Android Sauce Labs Real Devices then you can run the Android test with

    // If using the US DC
    mvn clean test -Dtest=NetworkThrottlingAndroidRDCTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=NetworkThrottlingAndroidRDCTest -Dregion=eu

The tests, which can be found [here](NetworkThrottlingAndroidRDCTest.java), will be executed on:

- Any available Android device

> The devices use *dynamic* allocation, meaning they will try to find an available device that matches a regular
expression.
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command
