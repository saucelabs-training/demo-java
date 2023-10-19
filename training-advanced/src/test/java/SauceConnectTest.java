import com.saucelabs.salsaverde.Browser;
import com.saucelabs.salsaverde.junit.SauceTestWatcher;
import com.saucelabs.saucebindings.SauceOptions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;
import org.openqa.selenium.By;

public class SauceConnectTest {
    public Browser browser;

    @Rule
    public SauceTestWatcher testWatcher = new SauceTestWatcher() {
        @Override
        protected void starting(Description description) {
            System.setProperty("SAUCE_USERNAME", System.getenv("SAUCE_USER_DEMO"));
            System.setProperty("SAUCE_ACCESS_KEY", System.getenv("SAUCE_KEY_DEMO"));

            SauceOptions sauceOptions = new SauceOptions();
            sauceOptions.setTunnelIdentifier("ORANGE");
            sauceOptions.setParentTunnel("titusfortner");

            this.setSauceOptions(sauceOptions);
            super.starting(description);
        }
    };

    @Before
    public void setup() {
        browser = new Browser(testWatcher.getDriver());
    }

    @Test
    public void SauceConnectDemo() {
        browser.goTo("https://www.saucedemo.com/");

        String email = "standard_user";
        String password = "secret_sauce";

        browser.element(By.id("user-name")).setText(email);
        browser.element(By.id("password")).setText(password);
        browser.element(By.className("btn_action")).click();
    }
}
