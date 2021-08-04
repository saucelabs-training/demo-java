# upload app to sauce storage
curl -F "payload=@/Users/nikolayadvolodkin/Documents/source/Appium/Android.SauceLabs.Mobile.Sample.app.2.2.1.apk" -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY" 'https://api.us-west-1.saucelabs.com/v1/storage/upload'


#upload app while setting file name
curl \
  -F "payload=@/Users/nikolayadvolodkin/Downloads/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.0.ipa" \
  -F name=sample-app-ios.ipa \
  -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY"  \
  'https://api.us-west-1.saucelabs.com/v1/storage/upload'
