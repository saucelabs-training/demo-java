import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;

public class SauceExecutionTest {
    @Test
    public void sauceExecution() throws MalformedURLException {
        // 1. Specify the 3 basic parameters of a SauceOptions instance
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setBrowserName(Browser.FIREFOX);
        sauceOptions.setBrowserVersion("73.0");
        sauceOptions.setPlatformName(SaucePlatform.WINDOWS_8);

        // 2. Create Session object with the Options object instance
        SauceSession session = new SauceSession(sauceOptions);

        // 3. Start Session to get the Driver
        RemoteWebDriver driver = session.start();

        // 4. Use the driver in your tests just like normal
        driver.get("https://www.saucedemo.com/");

        // 5. Stop the Session with whether the test passed or failed
        session.stop(true);
    }
}
