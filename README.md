# demonstration-scripts-java

This directory contains example scripts and dependencies for running automated Selenium tests on Sauce Labs using Java. You can use these scripts to test your Sauce Labs authentication credentials, the setup of your automated testing environment, and try out Sauce Labs features, like cross-browser testing. Feel free to copy these files or clone the entire directory to your local environment to experiment with creating your own automated Selenium tests!

**For Demonstration Purposes Only**

The code in these scripts is provided on an "AS-IS” basis without warranty of any kind, either express or implied, including without limitation any implied warranties of condition, uninterrupted use, merchantability, fitness for a particular purpose, or non-infringement. These scripts are provided for educational and demonstration purposes only, and should not be used in production. Issues regarding these scripts should be submitted through GitHub. These scripts are maintained by the Technical Services team at Sauce Labs.

## Setting Up a Java Selenium Environment

<p>These procedures will show you to set up a Selenium environment for Java. You first install the Java JDK and and then IntelliJ IDEA. You’ll then create a simple program to check that the Java environment is correct. Next, you’ll  set up the Selenium environment and testing framework for running your tests remotely on Sauce Labs. Finally, you will run a test that we provide to make sure your Selenium environment is correctly configured</p>
<h3>
  Installing the Java JDK
</h3>
<p>
  In this step you download and install the Java Development Kit (JDK).
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>In your web browser, go to the <a href="https://www.java.com/">
        <span style="color: rgb(17,85,204);">Java
      </a> site.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click <strong>Download</strong>, in the banner.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>The <strong>Java Downloads for All Operating System</strong>s page opens.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Scroll down to the version you need for your operating system.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Every version has specific instructions for how to download and install the JDK. If you need help, read them first. </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click the version for your operating system.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Accept the license agreement. <br/>You have to scroll through the entire document.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Once the package downloads, click on it and the installer starts. </p>
  </li>
</ol>
<h3>
  Installing IntelliJ IDEA
</h3>
<p>
  In this procedure you install the free community version of IntelliJ, an integrated development environment for Java applications.
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>In your web browser, go to <a href="https://www.jetbrains.com/idea/">
        <span style="color: rgb(17,85,204);">JetBrains IntelliJ IDEA
      </a>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click <strong>Download</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click the <strong>Download</strong> button for the Community version.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Once the program downloads, click on the package and the installer starts.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Accept the defaults. <br/>If you need to, select whether you want to run 32-bit or 64-bit and select the <code>.java</code> option in the <strong>Installation Options</strong> dialog.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click <strong>Install</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Once the installation is complete, select the option to start IntelliJ, then c<span style="letter-spacing: 0.0px;">lick <strong>Finish</strong>.
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select <strong>Do not import settings </strong>(the default).</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Accept the user agreement. <br/>You can decide if you want to share data or not.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select the UI theme, then s<span style="letter-spacing: 0.0px;">elect 
      <strong style="letter-spacing: 0.0px;">Skip All and Set Defaults</strong>
      <span style="letter-spacing: 0.0px;">.<br/>
      
      <span style="letter-spacing: 0.0px;">IntelliJ starts.
    </p>
  </li>
</ol>
<h3>Running a Simple Program</h3>
<p>
  In this procedure, you create a “Hello World” program and run it to make sure your Java environment is correct.
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>In IntelliJ, click <strong>Create New Project</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>The <strong>New Project</strong> dialog opens.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select <strong>Java</strong> (the default).</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>The Project SDK field should already show the Java JDK that you installed. If it doesn’t, click <strong>New</strong> and navigate to where the JDK is located.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click <strong>Next</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Check <strong>Create project from template</strong> and select <strong>Java, Hello World</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Name the project and click <strong>Finish</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Decide if you want to have Tips displayed.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>From the main toolbar, click <strong>Run</strong> and select <strong>Run Main</strong>.<br/>
      <span style="letter-spacing: 0.0px;">A window opens at the bottom of the screen. It should say <code>Hello World</code> and have an exit code of <code>0</code>. 
      <span style="letter-spacing: 0.0px;">Congratulations, you’ve validated your Java environment!
    </p>
  </li>
</ol>
<h3>
  Adding the Selenium Standalone Server
</h3>
<p>
  In this procedure, you add the Selenium Standalone Server to your Java project. Although you don’t need the server itself, the package contains components you do need to run tests remotely, such as the 
  <code>
    RemoteWebDriver
  </code>
  . 
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>In your browser, go to <a href="https://www.seleniumhq.org/download/">
        <span style="color: rgb(17,85,204);">SeleniumHQ Downloads
      </a>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Download the Selenium Standalone Server. <br/>You can download it to any folder you want. Many people make a separate folder for all their Selenium components.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>If you need to, unzip the file.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>In IntelliJ, click <strong>File</strong> and select <strong>Project Structure</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select <strong>Modules</strong>. </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Either click the <strong>green + sign</strong> or, on a Mac, enter <strong>command N</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select <strong>Jars or directories</strong>. </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Navigate to the folder with the Selenium Standalone Server and click <strong>Open</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click <strong>Apply</strong> and then click <strong>OK</strong>. <br/>You return to the main screen.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Expand <strong>External Libraries</strong>. <br/>You’ll see that the Selenium Standalone Server <code>.jar</code> file is there.</p>
  </li>
</ol>
<h3>Adding the SauceExamplesTestNG Framework</h3>
<p>
  The test script you’ll run relies on the SauceExamplesTestNG framework. This plugin comes with IntelliJ but you’ll need to add it to your project.
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>In IntelliJ, click <strong>File</strong> and select <strong>Project Structure</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select <strong>Modules</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Either click the <strong>green + sign</strong> or, on a Mac, enter <strong>command N</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select <strong>Jars or directories</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Navigate to <code>Applications/IntellJ IDEA CE/Contents/plugins/testng/lib/</code>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Select both <code>testng-plugin.jar</code> and <code>testng.jar</code>, then click <strong>Open</strong>.</p>
  </li>
  <li style="list-style-type: decimal;">
    <p>Click <strong>Apply</strong> and then click <strong>OK</strong>.<br/>
      <span style="letter-spacing: 0.0px;">The SauceExamplesTestNG <code>.jar</code> files appear in <strong>External Libraries</strong>.
    </p>
  </li>
</ol>
<h3>
  Adding the Test Script
</h3>
<p>
  In this step, you create a new Java class and download a Java test script, which we provide, to run your first test on Sauce Labs. The script opens up the Sauce Labs sample web application (a simulated online store) and logs in a sample user.
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>
      In IntelliJ, right-click on 
      <strong>
        src
      </strong>
      , point to 
      <strong>
        New
      </strong>
       and select 
      <strong>
        Java Class
      </strong>
      .
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Name the class 
      <code>
        InstantSauceTestNGTest1
      </code>
       and click 
      <strong>
        OK
      </strong>
      .
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Download the test script from <a href="https://github.com/saucelabs-training/demo-java/blob/master/SauceExamples/testng/InstantSauceTestNGTest1.java</a>.
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      In IntelliJ, delete any text that’s in the new class and paste in the test script.
    </p>
  </li>
</ol>
<h3>
  Getting Your Access Code on Sauce Labs
</h3>
<p>
  Before you can run your test on Sauce Labs, you need your user name and access key.
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>
      Log in to Sauce Labs at 
      <a href="http://www.saucelabs.com/">
        <span style="color: rgb(17,85,204);">www.saucelabs.com
      </a>
      . <br/>If you don’t have a Sauce Labs account you can create one and use Sauce Labs for free for two weeks.
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Click your name in the <strong>Account Profile</strong> menu in the upper-right corner.
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Click 
      <strong>
        User Settings
      </strong>
      .
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Scroll down to 
      <strong>
        Access Key
      </strong>
       and click 
      <strong>
        Show
      </strong>
      .
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Click the 
      <strong>
        Copy
      </strong>
       icon.
    </p>
  </li>
</ol>
<h3>
  Updating Your Test Script
</h3>
<p>
  You need to enter your Sauce Labs user name and access key into the test script. In IntelliJ, in the InstantSauceTestNGTest1 class, scroll down until you see:<br/>
  
</p>
<ac:structured-macro ac:macro-id="72b32d9d-4db3-40c4-8599-71862bab2099" ac:name="code" ac:schema-version="1">
  <ac:plain-text-body><![CDATA[String sauceUserName = "SAUCE_USERNAME";
String sauceAccessKey = "SAUCE_ACCESS_KEY";]]></ac:plain-text-body>
</ac:structured-macro>
<p>
   
  <span style="color: rgb(0,0,0);letter-spacing: 0.0px;">Enter your own credentials inside the quotes. You should be able to paste in your access key.
</p>
<h3>
  Running Your Script on Sauce Labs
</h3>
<p>
  You’re now ready to run your test on Sauce Labs.
</p>
<ol>
  <li style="list-style-type: decimal;">
    <p>
      In IntelliJ, right click anywhere in the <code>InstantSauceTestNGTest1</code> code, then select 
      <code>
        Run shouldOpenSafari()
      </code>
      . <br/>The test starts running.
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Log into the Sauce Labs web interface and click 
      <strong>
        Automated Tests
      </strong>
      .
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      You'll see that the test <strong>shouldOpenSafari</strong>
       is running.
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      To see the details, click 
      <strong>shouldOpenSafari</strong>
      . 
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      You'll see that <strong>Test Success</strong> is selected. 
    </p>
  </li>
  <li style="list-style-type: decimal;">
    <p>
      Click the 
      <strong>
        Play
      </strong>
       button. <br/>You'll see a video of the entire test run. The test opens up the Sauce Labs sample application. It then logs in to the application and clicks the LogIn button.
    </p>
  </li>
  <li style="list-style-type: decimal;">
      In IntelliJ, you’ll see a green check mark next to the message, 
      <code>“Tests passed: 1”</code>.   
  </li>
</ol>
<p>Congratulations, you've run your first test on Sauce! Now you can use our <a href="https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/">Platform Configurator</a> to change the desired capabilities in your test to try it on different combinations of platforms, operating systems, and browsers.</p>
