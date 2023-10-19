## Synchronization Examples

This code is provided on an "AS-IS” basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. Your tests and testing environments may require you to modify this framework. Issues regarding this framework should be submitted through GitHub. For questions regarding Sauce Labs integration, please see the Sauce Labs documentation at https://wiki.saucelabs.com/. This framework is not maintained by Sauce Labs Support.

### Training Plan

Start by showing http://watir.com/examples/wait.html

1. NoSynchTest will not pass because it needs to be synchronized

2. SynchExplicitTest shows the industry standard recommendation to get a test to pass
    a. It works
    b. The same code duplicated everywhere
    c. It never makes sense to take an action on an element that isn't displayed
    
3. SynchAbstractTest moves the synchronization code to a centralized place
    a. Same number of wire calls
    b. Less code duplication

4. DeclarativeTest shows a well abstracted "real world" tests for logging in
    a. Use it to show the basic extra wire calls that synchronized code means
    b. (Note: this is the minimal extra, would be more powerful if more extra synch was added)

5. SynchSalsaVerde shows an example of SalsaVerde usage that properly leverages the 
"Forgiveness over Permission" Approach

6. Show `User.bad()` results and the resulting failure and exception

7. Show how `loginSuccessfully()` gives a good exception message because it is opinionated

### Examples of Extra Synchronization:

* Is the Current Window Open
* Is there an alert
* Set the driver to the default browsing context
* Verify Collection tag names match expected
* Ensure element exists
* Ensure element visible
* Ensure element enabled
* Ensure element is not read only
* Ensure element is not stale
