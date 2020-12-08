import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class JUnit5W3CIETest {
    protected WebDriver driver;
    public Boolean result;

    @BeforeEach
    public void setup(TestInfo testInfo) throws MalformedURLException {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOptions.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        InternetExplorerOptions browserOptions = new InternetExplorerOptions();
        browserOptions.setCapability("platformName", "Windows 10");
        browserOptions.setCapability("browserVersion", "11.285");
        browserOptions.setCapability("sauce:options", sauceOptions);

        String sauceUrl = "https://ondemand.saucelabs.com:443/wd/hub";
        URL url = new URL(sauceUrl);
        driver = new RemoteWebDriver(url, browserOptions);
    }

    @Test
    public void shouldOpen() throws AssertionError {
        driver.navigate().to("https://www.saucedemo.com");
        String getTitle = driver.getTitle();
        Assertions.assertEquals(getTitle, "Swag Labs");
        if (getTitle.equals("Swag Labs")) {
            result = true;
        }else result = false;
    }
    @AfterEach
    public void teardown() {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (result ? "passed" : "failed"));
        driver.quit();
    }
}
