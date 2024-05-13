package com.examples.deep_link;

import com.google.common.collect.ImmutableMap;
import com.helpers.SauceAppiumTestWatcher;
import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.helpers.Constants.SAUCE_EU_URL;
import static com.helpers.Constants.SAUCE_US_URL;
import static com.helpers.Constants.region;

public class DeepLinkIosRDCTest {

    @Rule
    public TestName name = new TestName();

    //This rule allows us to set test status with Junit
    @Rule
    public SauceAppiumTestWatcher resultReportingTestWatcher = new SauceAppiumTestWatcher();

    private IOSDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        MutableCapabilities capabilities = new MutableCapabilities();
        MutableCapabilities sauceOptions = new MutableCapabilities();
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

        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("appium:automationName", "XCuiTest");
        capabilities.setCapability("appium:deviceName", "iPhone 15.*");
        // The feature only works since iOS 17
        capabilities.setCapability("appium:platformVersion", "17");
        capabilities.setCapability("appium:newCommandTimeout", 240);
        String appName = "iOS.MyDemoAppRN.ipa";
        capabilities.setCapability("app", "storage:filename=" +appName);

        sauceOptions.setCapability("name", name.getMethodName());
        sauceOptions.setCapability("build", "deepLink-job-1");
        List<String> tags = Arrays.asList("sauceDemo", "iOS","Deep Link");
        sauceOptions.setCapability("tags", tags);
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
        // iOS 17+ requires Appium 2+
        sauceOptions.setCapability("appiumVersion", "latest");

        capabilities.setCapability("sauce:options", sauceOptions);

        driver = new IOSDriver(url, capabilities);

        //Setting the driver so that we can report results
        resultReportingTestWatcher.setDriver(driver);
    }

    @Test
    public void openPageWithDeepLink () {
        System.out.println("Sauce - open page with deep link");

        // https://github.com/appium/appium-xcuitest-driver/blob/master/docs/reference/execute-methods.md#mobile-deeplink
        driver.executeScript("mobile: deepLink", ImmutableMap.of(
                "url", "https://saucelabs.com"
        ));

        // Verify we are in the Safari app
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) webDriver -> {
            @SuppressWarnings("unchecked")
            Map<String, Object> activeAppInfo = (Map<String, Object>) driver.executeScript(
                    "mobile: activeAppInfo"
            );
            return Objects.equals(activeAppInfo.get("bundleId"), "com.apple.mobilesafari");
        });
    }

}
