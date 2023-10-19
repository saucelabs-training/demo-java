import com.saucelabs.salsaverde.Browser;
import com.saucelabs.salsaverde.junit.SauceTestWatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

public class SauceDemoTest {
    public Browser browser;

    @Rule
    public SauceTestWatcher testWatcher = new SauceTestWatcher();

    @Before
    public void setup() {
        browser = new Browser(testWatcher.getDriver());
    }

    @Test
    public void SauceDemo() {
        browser.goTo("https://www.saucedemo.com/");

        String email = "standard_user";
        String password = "secret_sauce";

        browser.element(By.id("user-name")).setText(email);
        browser.element(By.id("password")).setText(password);
        browser.element(By.className("btn_action")).click();
    }
}
