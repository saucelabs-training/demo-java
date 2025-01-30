# Sauce Labs in Gitpod!
When launching the demo-java project in Gitpod, this is the project it will launch into.

Our Gitpod project is intended to be a simple, easy way to get started writing Selenium/Appium tests without having to 
configure an IDE, or Java, or really much of anything. You install a browser extension (link below), get your Sauce 
credentials, and you should be able to get going in 3-4 minutes!

## Prerequisites

- Gitpod Browser Extension: [Chrome, Edge](https://chromewebstore.google.com/detail/gitpod/dodmmooeoklaejobgleioelladacbeki?hl=en), [Firefox](https://addons.mozilla.org/en-US/firefox/addon/gitpod/)
- Github Account
- Sauce Account, including an Access Key (you can get your access key via User Settings in the Sauce Labs UI)

## Steps

- Click the "Open" button to the right of the "<> Code" button
- Gitpod will open a browser-based version of the VS Code Editor, and it will start to process
  - Here you'll see a bunch of logs fly by--Maven is downloading dependencies and running tests to make sure things are configured correctly
  - Once everything is done processing, enter this into the command line “Terminal” at the very bottom (using your username/access key from Sauce):

`$ SAUCE_USERNAME=<your.username> SAUCE_ACCESS_KEY=<your.accesskey>
mvn clean test`

If you’re able to complete these steps, you should be ready to work on scripts and execute them on Sauce!

Note that any changes you make within Gitpod will be lost once you close the session. To retain changes, you'll need to copy them locally or commit them to your own fork within Github.
