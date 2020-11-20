package Tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {
    public RemoteWebDriver driver;

    @Rule
    public SauceTestWatcher watcher = new SauceTestWatcher();

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return String.format("%s", super.getMethodName());
        }
    };

    @Before
    public void setup() throws MalformedURLException {
        String username = System.getenv("SAUCE_USERNAME");
        String accessKey = System.getenv("SAUCE_ACCESS_KEY");
        String methodName = name.getMethodName();

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("name", methodName);
        sauceOpts.setCapability("username", username);
        sauceOpts.setCapability("accessKey", accessKey);
        sauceOpts.setCapability("screenResolution", "1920x1200");

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setLogLevel(FirefoxDriverLogLevel.INFO);

        firefoxOptions.setCapability("sauce:options", sauceOpts);
        firefoxOptions.setCapability("browserVersion", "latest");
        firefoxOptions.setCapability("platformName", "Windows 10");

        String sauceUrl = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, firefoxOptions);
        watcher.setDriver(driver);
    }

    private static class SauceTestWatcher extends TestWatcher {
        private RemoteWebDriver driver;

        public void setDriver(RemoteWebDriver driver) {
            this.driver = driver;
        }

        @Override
        protected void failed(Throwable e, Description description) {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=failed");
        }

        @Override
        protected void succeeded(Description description) {
            ((JavascriptExecutor) driver).executeScript("sauce:job-result=passed");
        }

        @Override
        protected void finished(Description description) {
            driver.quit();
        }
    }
}
