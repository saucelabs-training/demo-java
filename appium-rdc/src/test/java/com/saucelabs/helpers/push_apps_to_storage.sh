#!/bin/bash

## US
# Android
curl \
  -F "payload=@../apps/Android.MyDemoAppRN.apk" \
  -F name=Android.MyDemoAppRN.apk \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.us-west-1.saucelabs.com/v1/storage/upload'
# iOS
curl \
  -F "payload=@../apps/iOS.MyDemoAppRN.zip" \
  -F name=iOS.MyDemoAppRN.zip \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.us-west-1.saucelabs.com/v1/storage/upload'
curl \
  -F "payload=@../apps/iOS.MyDemoAppRN.ipa" \
  -F name=iOS.MyDemoAppRN.ipa \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.us-west-1.saucelabs.com/v1/storage/upload'

## EU
# Android
curl \
  -F "payload=@../apps/Android.MyDemoAppRN.apk" \
  -F name=Android.MyDemoAppRN.apk \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.eu-central-1.saucelabs.com/v1/storage/upload'
# iOS
curl \
  -F "payload=@../apps/iOS.MyDemoAppRN.zip" \
  -F name=iOS.MyDemoAppRN.zip \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.eu-central-1.saucelabs.com/v1/storage/upload'
curl \
  -F "payload=@../apps/iOS.MyDemoAppRN.ipa" \
  -F name=iOS.MyDemoAppRN.ipa \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.eu-central-1.saucelabs.com/v1/storage/upload'
