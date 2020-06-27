package com.yourcompany.listeners;

import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.net.URL;
import java.util.Map;

public class SauceTestListener implements ITestListener {

   // private DeviceUtils.RunType runType;
    final String CAP_PREFIX = "cap:";
    final String SAUCE_PREFIX = "sauce:";

    private RunType runType;

    public enum RunType { LOCAL, SAUCE }

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<String> sessionId = new ThreadLocal<>();

    public WebDriver getDriver() {
        return webDriver.get();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testName = iTestResult.getName();
        System.out.println("Sauce - In Hook onTestStart. Test name: " + testName);

        // Get all test parameter
        Map<String, String> allParameters = iTestResult.getTestContext().getCurrentXmlTest().getAllParameters();

        MutableCapabilities capabilities = new MutableCapabilities();

        // Add the parameters to the capabilities
        capabilities = addDriverCapabilities(capabilities,allParameters);

        switch(runType) {
            case LOCAL:
                createLocalDriver(capabilities);
                break;
            case SAUCE:
                createSauceDriver(capabilities);
                break;
        }

        // update test name and tags
        try {
            if (runType.equals(RunType.SAUCE)) {
                ((JavascriptExecutor) getDriver()).executeScript("sauce:job-name=" + iTestResult.getName());
                String[] tags = iTestResult.getTestContext().getAllTestMethods()[0].getGroups();
                String strTags = String.join(",", tags);
                ((JavascriptExecutor) getDriver()).executeScript("sauce:job-tags=" + strTags);
            }
        } catch (Exception e){
            System.out.println("Sauce - In Hook onTestStart. Problem to update test details");
        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Sauce - In Hook onTestSuccess");

        try {
            if (runType.equals(RunType.SAUCE)) {
                updateTestResult(iTestResult);
            }
        }
        finally {
            getDriver().quit();
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("Sauce - In Hook onTestFailure");

        try {
            if (runType.equals(RunType.SAUCE)) {
                updateTestResult(iTestResult);
            }
        }
        finally {
            getDriver().quit();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Sauce - In Hook onTestSkipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Sauce - In Hook onTestFailedButWithinSuccessPercentage");
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("Sauce - In Hook onStart");

    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("Sauce - In Hook onFinish");

    }

    private void updateTestResult(ITestResult iTestResult) {
        ((JavascriptExecutor)getDriver()).executeScript("sauce:job-result=" + (iTestResult.isSuccess() ? "passed" : "failed"));
    }


    private void sendCommentToReport(String comment){
        if (runType.equals(RunType.SAUCE)) {
            ((JavascriptExecutor) getDriver()).executeScript("sauce:context=" + comment);
        }
    }

    private  void createLocalDriver(MutableCapabilities capabilities) {

        try {
            webDriver.set(new ChromeDriver());
        } catch (Exception e) {
            System.out.println("*** Problem to create the local driver " + e.getMessage());
            throw new RuntimeException(e);
        }


    }
    private  void createSauceDriver(MutableCapabilities capabilities) {
        String username = System.getenv("SAUCE_USERNAME");
        String accesskey = System.getenv("SAUCE_ACCESS_KEY");

        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

        try {

            System.out.println("*** Sauce - create webdriver for desktop " );
            webDriver.set(new RemoteWebDriver(new URL(SAUCE_REMOTE_URL), capabilities));

            String id = ((RemoteWebDriver) getDriver()).getSessionId().toString();
            sessionId.set(id);
            System.out.println("*** Sauce - Session id for desktop is: " + id );


        } catch (Exception e) {
            System.out.println("*** Problem to create the driver " + e.getMessage());
            sendCommentToReport(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public MutableCapabilities addDriverCapabilities(MutableCapabilities capabilities,Map<String, String> driverParams){
        String api = driverParams.get("API").toLowerCase();
        switch (api.toLowerCase()) {
            case "w3c":
                runType = RunType.SAUCE;
                capabilities = addW3CCap(capabilities, driverParams);
                break;
            case "legacy":
                runType = RunType.SAUCE;
                addLegacyCap(capabilities, driverParams);
                break;
            case "local":
                runType = RunType.LOCAL;
                addLocalCap(capabilities, driverParams);
                break;
        }

        return capabilities;
    }

    private void addLegacyCap(MutableCapabilities capabilities, Map<String, String> driverParams) {
        setDriverCapabilities(capabilities, driverParams);
    }


    private void addLocalCap(MutableCapabilities capabilities, Map<String, String> driverParams) {

        String browser = driverParams.get("browser").toLowerCase();
        if (browser.equals("chrome")) {
            System.out.println("*** Sauce - add Chrome local capabilities ***");
            String driverPath = driverParams.get("webdriver.chrome.driver");
            System.setProperty("webdriver.chrome.driver",driverPath);
        }
    }

    private  MutableCapabilities addW3CCap(MutableCapabilities capabilities, Map<String, String> driverParams) {

        String browser = driverParams.get("browser").toLowerCase();
        if (browser.equals("chrome")) {
            System.out.println("*** Sauce - Add chrome W3C driver ***");
            ChromeOptions caps = new ChromeOptions();
            caps.setExperimentalOption("w3c", true);
            capabilities = caps;
        }
        else if (browser.equals("firefox")) {
            System.out.println("*** Sauce - Add firefox W3C driver ***");
            capabilities = new FirefoxOptions();
        }
        else if (browser.equals("safari")) {
            System.out.println("*** Sauce - Add Safari W3C driver ***");
            capabilities = new SafariOptions();
        }

        setDriverCapabilities(capabilities, driverParams);

        if (runType.equals(RunType.SAUCE)) {
            MutableCapabilities sauceOptions = new MutableCapabilities();
            driverParams.forEach((k, v) -> {
                if (k.startsWith(SAUCE_PREFIX)) {
                    // remove the prefix
                    String capability = k.replace(SAUCE_PREFIX, "");
                    sauceOptions.setCapability(capability, v);
                }
            });
            capabilities.setCapability("sauce:options", sauceOptions);
        }

        return capabilities;
    }

    private  void setDriverCapabilities(MutableCapabilities capabilities, Map<String, String> driverParams) {
        driverParams.forEach((k,v)->{

            if(k.startsWith(CAP_PREFIX)){
                // remove the prefix
                String capability =  k.replace(CAP_PREFIX,"");
                if (v.equals("true") || v.equals("false")){
                    // boolean
                    capabilities.setCapability(capability, Boolean.parseBoolean(v));
                } else {
                    capabilities.setCapability(capability, v);
                }
            }
        });
    }

}
