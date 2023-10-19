## Advanced Introduction to Sauce Labs

This code is provided on an "AS-IS” basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. Your tests and testing environments may require you to modify this framework. Issues regarding this framework should be submitted through GitHub. For questions regarding Sauce Labs integration, please see the Sauce Labs documentation at https://wiki.saucelabs.com/. This framework is not maintained by Sauce Labs Support.

### Training Plan

1. `LocalExecutionTest` shows off running locally

2. `SauceExecutionTest` shows the basics of running on Sauce Labs
    a. This uses the Sauce Bindings example straight from the website
    
3. `ExtraSauceTest` shows all of the main Sauce Labs specific values that can be used

4. `TestWatcherTest` shows sending passing & failing information to Sauce
    a. This uses SalsaVerde's `com.saucedemo.SauceTestWatcher` class

5. `SauceDemoTest` shows running an actual authentication test to look at commands list
    a. Note this is using SalsaVerde syntax

6. `ExtendedDebuggingTest` - Sets Extended debugging values to show in UI

7. `SauceConnectTest` - kick off a tunnel locally and show the sub-account:
    a. Expects these sc flags: `--tunnel-identifier ORANGE --shared-tunnel --no-remove-colliding-tunnels --pidfile /tmp/pid0.log'`
    b. Requires overriding Sauce Bindings defaults with different username/access key values 


