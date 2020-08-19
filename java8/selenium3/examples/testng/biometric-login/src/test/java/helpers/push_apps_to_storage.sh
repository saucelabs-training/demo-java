#!/bin/bash
# Uncomment for which data center you need
# US
#curl -u $SAUCE_USERNAME:$SAUCE_ACCESS_KEY -X POST -H "Content-Type: application/octet-stream" https://saucelabs.com/rest/v1/storage/$SAUCE_USERNAME/sample-app-android.apk?overwrite=true --data-binary @.../apps/Android.SauceLabs.Mobile.Sample.app.2.3.0.apk
#curl -u $SAUCE_USERNAME:$SAUCE_ACCESS_KEY -X POST -H "Content-Type: application/octet-stream" https://saucelabs.com/rest/v1/storage/$SAUCE_USERNAME/sample-app-ios-real.zip?overwrite=true --data-binary @../apps/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.3.0.ipa

# EU
curl -u $SAUCE_USERNAME:$SAUCE_ACCESS_KEY -X POST -H "Content-Type: application/octet-stream" https://eu-central-1.saucelabs.com/rest/v1/storage/$SAUCE_USERNAME/sample-app-android.apk?overwrite=true --data-binary @../apps/Android.SauceLabs.Mobile.Sample.app.2.3.0.apk
curl -u $SAUCE_USERNAME:$SAUCE_ACCESS_KEY -X POST -H "Content-Type: application/octet-stream" https://eu-central-1.saucelabs.com/rest/v1/storage/$SAUCE_USERNAME/sample-app-ios-real.ipa?overwrite=true --data-binary @../apps/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.3.0.ipa
