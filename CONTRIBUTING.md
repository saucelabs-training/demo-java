# Adding Code To demo-java

* Clone the repository

* Decide if your code example is an example or a framework

An **example** is some code that wants to demonstrate a feature. 
For example, parallelization with JUnit4, parallelization with
Junit5,
biometric authentication, simple Appium iOS test.

A **best-practice** is solution that shows off
how to use a specific technology combination in the optimal way
according to the Solution Architects team. You might have a
**best-practice** that shows how to correctly use Cucumber with Junit4
or Cucumber with Junit5 or maybe a data driven framework with
TestNg.
**best-practice** examples are not as common as code examples.

## Add relevant code

### Repository Structure

The correct repository structure is:

```text
Generic Structure
It's basically Selenium/Appium/API in the future
Followed by testRunnerVersion(which really only applies for Junit)
Then it's either an example (most common) or a best-practice aka framework

|-- demo-java
    |-- selenium.testRunnerVersion.examples (Java module)
    |-- selenium.testRunnerVersion.best-practice (Java module)
    |-- selenium.testRunnerVersion.examples.cucumber (Java module)
    |-- selenium.testRunnerVersion.best-practice.cucumber (Java module)
    |-- visuale2e.testRunnerVersion.examples (Java module)
    |-- visuale2e.testRunnerVersion.best-practice (Java module)
    |-- appium.testRunnerVersion.examples (Java module)
    |-- appiumVersion.testRunnerVersion.best-practice (Java module)
```

```text
Specific Structure With Examples
|-- demo-java
    |-- selenium.junit4.examples (Java module)
        |-- src
            |-- main
            |-- test
                |-- java
                    |-- com.saucedemo
                        |-- Junit4InParallelTests.java
                        |-- Junit4TestStatusUpdate.java
                        |-- Junit4DataDriven.java
                        |-- PerformanceTesting.java
        |-- selenium.junit4.best-practice (Java module)
            |-- src
                |-- main
                    |-- java
                        |-- com.saucedemo
                            |-- ShoppingCartPage.java
                            |-- LoginPage.java
                |-- test
                    |-- java
                        |-- com.saucedemo
                            |-- ShoppingCartTests.java
                            |-- LoginTests.java
                            |-- LoginAPITests.java
        |-- selenium.testng.examples.cucumber (Java module)
        |-- selenium.testng.best-practice.cucumber (Java module)
        |-- ...
```

## FAQs

### Why should I separate my code in this manner?

The key ideas behind this organization are visibility and 
reusability for the clients and the team. A mature customer may need
a performance testing code example using Junit5. On the other
hand, a less mature, but very valuable customer may need the 
same exact code sample but using Junit3. If we create
this code sample for one of the customers, wouldn't it
also be nice to make these visible to all other customers
that desire a specific combination of technologies?

But where would such code examples go? 

In the above structure that's easy for everyone to understand.

### But I don't want to create a code example for 10 different technologies!

There is no requirement to have a code **example** for every single tech combination.

Only create what's needed at the time. 
All we ask is that if you create a code example for one client
using a specific combination of technologies (ex Junit3 test status reporting), 
then make
that available to all customers, SAs, and SEs. 

Let your work be reusable and visible for the future instead
of being hidden somewhere in a private repository. It's highly
likely that there is a customer, or an engineer that's 
spending time recreating a code sample that you already 
created in your private repo.

### How will someone find my beautiful code sample?

There are code examples that are popular 
(Getting started samples) and there are code examples that
aren't so popular (using Junit3 to update test status).

If you feel that your code example needs to be front and
center then simply add it to the main [README](README.md).
We would be happy to see your excellent code there 😁
