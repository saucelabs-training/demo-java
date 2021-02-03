package Tests;

import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class MSEdgeTest {

    @Test
    public void edgeExecution() throws MalformedURLException {
        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("username", System.getenv("SAUCE_USERNAME"));
        sauceOpts.setCapability("accessKey", System.getenv("SAUCE_ACCESS_KEY"));

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setExperimentalOption("excludeSwitches",
                Collections.singletonList("disable-popup-blocking"));

        edgeOptions.setCapability("sauce:options", sauceOpts);

        String sauceUrl = "https://ondemand.us-west-1.saucelabs.com/wd/hub";
        URL url = new URL(sauceUrl);
        RemoteWebDriver driver = new RemoteWebDriver(url, edgeOptions);

        driver.get("http://www.popuptest.com/popuptest1.html");
        driver.quit();
    }
}
