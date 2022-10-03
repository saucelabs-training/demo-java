# Using image injection on Sauce Labs Real Devices
This folder contains examples for using image injection on real devices for:

- [Android real devices on the Sauce Labs Cloud](#run-tests-on-sauce-labs-android-real-devices)
- [iOS real devices on the Sauce Labs Cloud](#run-tests-on-sauce-labs-ios-real-devices)

> NOTE: this feature is **NOT** supported for emulators and simulators!!

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

## Run tests on Sauce Labs Android real devices
If you want to run the tests on Sauce Labs real devices then you can run the Android test with

    // If using the US DC
    mvn clean test -Dtest=ImageInjectionAndroidTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=ImageInjectionAndroidTest -Dregion=eu
    
The tests will be executed on a Samsung Galaxy 12.

> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

## Run tests on Sauce Labs iOS real devices
If you want to run the tests on Sauce Labs real devices then you can run the iOS test with

    // If using the US DC
    mvn clean test  -Dtest=ImageInjectionIosTest -Dregion=us
    
    // If using the EU DC
    mvn clean test -Dtest=ImageInjectionIosTest -Dregion=eu
    
The tests will be executed on an iPhone 14.
> NOTE: Make sure you are in the folder `appium-app-examples` when you execute this command

## Special Thanks
Huge thanks to [Wim Selles](https://github.com/wswebcreation). He develops and maintains the [Sauce Labs demo-js repository](https://github.com/saucelabs-training/demo-js). 
The js repo inspired the examples in this repository. Thanks my friend.
