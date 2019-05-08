# Java Demonstration Scripts

[![CircleCI](https://circleci.com/gh/saucelabs-training/demo-java/tree/master.svg?style=svg)](https://circleci.com/gh/saucelabs-training/demo-java/tree/master)

This directory contains example scripts and dependencies for running automated Selenium tests on Sauce Labs using Java. You can use these scripts to test your Sauce Labs authentication credentials, the setup of your automated testing environment, and try out Sauce Labs features, like cross-browser testing. Feel free to copy these files or clone the entire directory to your local environment to experiment with creating your own automated Selenium tests!

#### For Demonstration Purposes Only

The code in these scripts is provided on an "AS-IS” basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. These scripts are provided for educational and demonstration purposes only, and should not be used in production. Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by the Technical Services team at Sauce Labs.
<br />

## Prerequisites

These procedures will show you to set up a Selenium environment for Java. The scripts in this repository allow you run a simple automated test to validate your Selenium environment and your [saucelabs.com](https://app.saucelabs.com/login) account credentials.

In order to participate in the hands-on portion of the workshop you must complete the following prerequisite installation and configuration steps:

* Install [Git](#install-git)
* Install [IntelliJ (or another IDE)](#install-intellij)
* Install [JDK](#install-the-jdk)
* Install [Maven](#install-maven)


[Project Setup](#setup-the-project) consists of the following steps:
* [Import the Project](#import-the-project)
* [Set Your Sauce Labs Credentials](#set-your-sauce-labs-credentials)
* [Run a Maven Test](#run-a-maven-test) 

***WARNING!*** In order to run the scripts and participate in this workshop you must have a valid 
[Sauce Labs account](https://app.saucelabs.com/signup) with the relevant amount of 
[VM concurrency](https://saucelabs.com/pricing). 

For this workshop, we will run between 1 and 40 threads simultaneously,
and while we understand that a free account won't give you enough concurrency to run these directly, the instructor
will adequately demonstrate the the system behavior. As long as you can execute even a single test against the Sauce
Labs platform with this code, you can have confidence that the code will scale to as many threads as you care to execute. 

<br />

### Install Git

[Git](https://git-scm.com/doc) is a version control system that lets you check out code from a repository, 
work with that code on your own branch, and then merge that code with any changes that have been made by other developers. 
Git is an essential tool for distributed development teams, and is a critical component of the continuous 
integration/continuous development toolchain.

#### MacOSX:

1. Go to [https://git-scm.com/downloads](https://git-scm.com/downloads).
2. Under **Downloads**, click **Mac OS X**.
3. When the download completes, double-click the `.dmg` file open the installer package.
4. Double-click the installer package to begin the installation.
    > *Security Warning*
    >
    > You may see a warning message that the package can't be opened because it's not from a recognized developer. 
    If this happens, go to System Preferences > Security and Privacy Settings, and click Open Anyway.
5. Click **Continue** for the installation, and enter your local password to authorize the installation.

#### Windows:

1. Go to [https://git-scm.com/downloads](https://git-scm.com/downloads)
2. Under **Downloads**, click on **Windows**.
3. When the dialog opens asking if you want to allow the app to make changes to your device, click Yes.
4. Follow the steps in the setup wizard to complete the installation. You should accept all the default settings.

<br />

### Install IntelliJ

[IntelliJ](https://www.jetbrains.com/idea/) is an integrated development environment that incorporates several tools for developing and running Java code. You will be using IntelliJ to write and edit the sample Selenium scripts used in the exercises.  For these exercises you only need to download the free Community edition.

#### MacOSX:

1. Go to [https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)
2. Click **Download**.
3. On the **Downloads** page, select **macOS**.
4. Under **Community**, click **Download**.
5. When the download completes, double-click the .dmg file open the installer package.
6. Double-click the installer package to begin the installation.
7. Drag and drop the IntelliJ icon into the **Applications** folder.

#### Windows:

1. Go to [https://www.jetbrains.com/idea/](https://www.jetbrains.com/idea/)
2. Click **Download**.
3. On the **Downloads** page, select **Windows**.
4. Under **Community**, click **Download**.
5. When the download completes, double-click the `.exe` file to launch the installation wizard. 
You should accept all the default settings.

<br />

### Install the JDK

The [Java SE Developer Kit](http://www.oracle.com/technetwork/java/javase/overview/index.html) lets you develop and 
deploy Java applications on desktops and servers. It is needed to compile our test code.

This project requires JDK version 1.8 at the least.

#### MacOSX:

1. Go to [the JDK downloads](https://www.oracle.com/technetwork/java/javase/downloads/index.html) page, locate the latest release, select the download button.
2. Under **Java SE Development Kit {version}**, select the **Accept License Agreement** radio button.
3. Click the download link for **Mac OS**.
4. When the download completes, double-click the `.dmg` file open the installer package.
Double-click the installer package to begin the installation.

#### Windows:

1. Go to [the JDK downloads](https://www.oracle.com/technetwork/java/javase/downloads/index.html) page, locate the latest release, select the download button.
2. Under **Java SE Development Kit {version}**, select the **Accept License Agreement** radio button.
3. Click the download link for **Windows x64**.
4. When the download completes, double-click the `.exe` file open the installer package.
5. Double-click the installer package to begin the installation. You should accept all the default settings.

<br />

### Install Maven

Maven is a build automation and project management tool use for managing project builds, dependencies, and documentation. It uses a project object model (pom.xml) to manage Java-based projects. With our use case, it's very useful for configuring and managing test suites.

#### MacOSX:

1. Go to Maven Apache website and [download](https://maven.apache.org/install.html) the following package: `apache-maven-<version>-bin.tar.gz`
2. Extract the archive
    ```
    $ tar -xvf apache-maven-<version>-bin.zip
    ```
3. Add the `bin` directory of the extracted directory (`apache-maven-<version>`) to the `PATH` variable:
    ```
    $ export M2=$M2_HOME/bin
    $ export PATH=$M2:$JAVA_HOME/bin:$PATH
    ```
    > ***WARNING!***: Make sure you've set `JAVA_HOME` othewise `mvn` commands won't run. For instructions on how to set `JAVA_HOME`, visit this [link](https://docs.oracle.com/cd/E19182-01/821-0917/inst_jdk_javahome_t/index.html)

4. Check to see if maven installed correctly:
    ```
    $ mvn -version
    ```

#### Windows:

1. Go to Maven Apache website and [download](https://maven.apache.org/install.html) the following package: `apache-maven-<version>-bin.zip`
2. Unpack the archive using an archive tool (for example WinZip)
3. Add the unpacked distribution’s `bin` directory to your user `PATH` environment variable by:
    1. Open up the system properties (WinKey + Pause) 
    2. Select the **Advanced** tab, and the **Environment Variables** button
    3. Add/Select the **`PATH`** variable in the user variables with the value:
        ```
        C:\Program Files\apache-maven-3.6.0\bin
        ```
4. Open a new command prompt (Winkey + R then type cmd) and run `mvn -v` to verify the installation.

<br />

### Setup the Project

#### Import the Project

1. Create a directory on your machine.
2. Clone this repository into said directory.
    ```
    $ git clone https://github.com/saucelabs-training/saucecon19-parallel-workshop.git
    ```
2. Import the project into your IntelliJ (or IDE of your choice) as a **Maven Project**.
3. Click through the prompts, and confirm when it asks to **Import from Sources**
4. Choose the **saucedemo-parallel** directory as the **root** directory of the project.

#### Set Your Sauce Labs Credentials
1. Copy your Sauce Labs **username** and **accessKey** in the [User Settings](https://app.saucelabs.com/user-settings) section of the [Sauce Labs Dashboard](https://app.saucelabs.com/dashboard/builds).
2. Open a Terminal window (command prompt for Windows) and set your Sauce Labs Environment variables:   
   ###### Mac OSX:
   ```
   $ export SAUCE_USERNAME="username"
   $ export SAUCE_ACCESS_KEY="accessKey"
   ```
   ###### Windows:
   ```
   > set SAUCE_USERNAME="username"
   > set SAUCE_ACCESS_KEY="accesKey"
   ```
   > To set an environment variables permanently in Windows, you must append it to the `PATH` variable.
   
   > Go to **Control Panel > System > Windows version > Advanced System Settings > Environment Variables > System Variables > Edit > New**
   
   > Then set the "Name" and "Value" for each variable
   
9. Test the environment variables
    ###### Mac OSX:
    ```
    $ echo $SAUCE_USERNAME
    $ echo $SAUCE_ACCESS_KEY
    ```
    ###### Windows:
    ```
    > echo %SAUCE_USERNAME%
    > echo %SAUCE_ACCESS_KEY%
    ```
    
    > ***WARNING FOR UNIX USERS!***:
    > If you have problems setting your environment variables, try the following options:
    
    > ##### Option 1:
    Run the following commands in your terminal:
    ```
    $ launchctl setenv SAUCE_USERNAME $SAUCE_USERNAME
    $ launchctl setenv SAUCE_ACCESS_KEY $SAUCE_ACCESS_KEY
    ```
    > ##### Option 2:
    Refresh your bash session by running the following command: 
    ```
    $ source ~/.bashrc
    ``` 
    > ##### Option 3:
    Update **`.bash_profile`** to globally set environment variables. For example your file may look something like this:
    ```
    export JAVA_HOME=$(/usr/libexec/java_home)
    export PATH="$PATH:/usr/local/bin"
    export PATH=$M2:$JAVA_HOME/bin:$PATH
    export M2_HOME=/usr/local/apache-maven-<version>
    export M2=$M2_HOME/bin
    export SAUCE_USERNAME="xxx"
    export SAUCE_ACCESS_KEY="XXXXXXX-XXXX-XXXX-XXXXXXXXXXX"
    ```

#### Run a Maven Test

1. Run the following command to update any package dependencies:
    ```
    $ mvn dependency:resolve
    ```
2. Then run the following command to compile your test code:
    ```
    $ mvn test-compile
    ```
3. Finally, run the following test:
    ```
    $ mvn clean test
    ```