package com.saucelabs.tests;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.saucelabs.tests.Config.region;

public class BaseTest {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    private String SAUCE_EU_URL = "https://ondemand.eu-central-1.saucelabs.com/wd/hub";
    private String SAUCE_US_URL = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
    private String SAUCE_CAP = "sauce_";

    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        System.out.println("BeforeMethod hook");
        String methodName = method.getName();
        URL url;

        switch (region) {
            case "us":
                url = new URL(SAUCE_US_URL);
                break;
            case "eu":
            default:
                url = new URL(SAUCE_EU_URL);
                break;
        }

        boolean isBuildCap = false;
        MutableCapabilities caps = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();

        for (Map.Entry<String, String> entry : Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getAllParameters().entrySet()) {
            String k = entry.getKey();
            String v = entry.getValue();

            if (k.startsWith(SAUCE_CAP)) {
                // Sauce capability
                String sauceCap = k.replaceFirst(SAUCE_CAP, "");
                if (sauceCap.equals("build")) {
                    isBuildCap = true;
                }

                if (v.contains(" ")) {
                    // handle a list such as in tags cap
                    List<String> capList = Arrays.asList(v.split(" "));
                    sauceOptions.setCapability(sauceCap, capList);
                } else {
                    sauceOptions.setCapability(sauceCap, v);
                }
            } else {
                caps.setCapability(k, v);
            }
        }

        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        sauceOptions.setCapability("name", methodName);

        if (!isBuildCap) { //handle build cap
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH");
            String buildLocal = "sauceDemo-" +dateTime.format(formatter);
            String buildVal = System.getenv("BUILD_TAG");
            sauceOptions.setCapability("build", buildVal == null ? buildLocal : buildVal);
        }

        // handle tunnel
        String tunnelName = System.getenv("SAUCE_TUNNEL_NAME");
        if (!(tunnelName == null)) { //handle build cap
            System.out.println("*** tunnel name is " + tunnelName);
            sauceOptions.setCapability("tunnelName", tunnelName);
        }

        caps.setCapability("sauce:options", sauceOptions);

        try {
            driver.set(new AppiumDriver(url, caps));
        } catch (Exception e) {
            System.out.println("*** Problem to create the driver " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        System.out.println("AfterMethod hook");
        try {
            boolean bSuccess = result.isSuccess();
            ((JavascriptExecutor) getDriver()).executeScript("sauce:job-result=" + (bSuccess ? "passed" : "failed"));
            if (!bSuccess)
                ((JavascriptExecutor) getDriver()).executeScript("sauce:context=" +result.getThrowable().getMessage());

            // Print the report execution URL:
            System.out.println("Execution URL: " + getDriver().getCapabilities().getCapability("appium:testobject_test_report_url"));
        } finally {
            System.out.println("Release driver");
            getDriver().quit();
        }
    }

    public AppiumDriver getDriver() {
        return driver.get();
    }

}
