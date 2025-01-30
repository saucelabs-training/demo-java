# Testing With Sauce Labs

This project demonstrates:

✅ Browser testing on desktop

✅ Browser testing on mobile

✅ Visual testing

✅ CICD pipeline executed on push and PR

## Test Tactics for a Winning Test Strategy

| Expected Behavior                                  | Tested? | Test Type                                                        | Rationale                                                                                                 | Tech                                                                     |
|----------------------------------------------------|---------|------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| Every web page of the app looks correct on desktop | ✅       | Visual test                                                      | A visual test efficiently validates app rendering                                                         | [Selenium](https://selenium.dev)                                         |
| Every web page of the app looks correct on mobile  | ✅       | Visual test                                                      | A visual test efficiently validates app rendering                                                         | [Selenium](https://selenium.dev)                                         |
| A user can successfully check out on desktop       | ✅       | Functional web test                                              | Functional testing of the most critical functionality is important                                        | [Selenium](https://selenium.dev)                                         |
| A user can successfully check out on mobile        | ✅       | Functional mobile test                                           | Although redundant to a functional web test, it's relatively easy to test this on a mobile device as well | [Appium](https://appium.io/docs/en/latest/)                              |
| App is accessibility friendly                      | 🙅‍♂️   | Selenium web test                                                | Accessibility in applications is becoming extremely important                                             | [Selenium](https://selenium.dev), [axe-core](https://www.deque.com/axe/) |
| Front-end performance is at least an A             | 🙅‍♂️   | Front-end performance test                                       | Front-end performance is an important aspect of any digital quality effort                                | [Selenium](https://selenium.dev), [Sauce Labs](https://saucelabs.com/)   |
| Test code runs on every commit in under 5 minutes  | 🙅‍♂️   | CICD                                                             | Slow feedback makes it hard to iterate                                                                    | [Github Actions](https://github.com/features/actions)                    |
| App is secure                                      | 🙅‍♂️   | Not covered here, but something to consider for testing strategy |                                                                                                           |


## ⚙️Setup

* For full instructions on getting tests up and running, see the main [README](../README.md) file 

## Running all tests

* Run all the tests inside the `best-practice` directory

```
cd best-practice 
mvn clean test
```





