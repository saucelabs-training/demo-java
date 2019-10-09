# Java Demo Prerequisites

Before attempting an automated test, ensure you've installed the following software:
* [Git](#install-git)
* [IntelliJ (or another IDE)](#install-intellij)
* [JDK](#install-the-jdk)
* [Maven](#install-maven)

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
2. Unpack the archive using an archive tool (for emusim_testng WinZip)
3. Add the unpacked distribution’s `bin` directory to your user `PATH` environment variable by:
    1. Open up the system properties (WinKey + Pause) 
    2. Select the **Advanced** tab, and the **Environment Variables** button
    3. Add/Select the **`PATH`** variable in the user variables with the value:
        ```
        C:\Program Files\apache-maven-3.6.0\bin
        ```
4. Open a new command prompt (Winkey + R then type cmd) and run `mvn -v` to verify the installation.

<br />