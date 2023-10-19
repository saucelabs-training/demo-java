import com.saucelabs.salsaverde.Browser;
import com.saucelabs.salsaverde.elements.Executor;
import com.saucelabs.salsaverde.junit.BaseTestWatcher;
import com.saucelabs.salsaverde.junit.SauceTestWatcher;
import com.saucelabs.salsaverde.pages.PageObject;
import data.UserData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import pages.LoginPage;


public class DeclarativeTest {
    public Browser browser;

    @Rule
    public BaseTestWatcher testWatcher = new SauceTestWatcher();

    @Before
    public void setup() {
        browser = new Browser((RemoteWebDriver) testWatcher.getDriver());

        // Explicitly sets browser rather than hiding it; it's just one line
        PageObject.setBrowser(browser);
        Executor.waitTime = 5;
    }

    @Test
    public void loginSuccessfully() {
        // Use contextually informative static methods to create Data Objects
        UserData userData = UserData.valid();

        // Don't put the magic in the constructor, have a method that does what you expect it to
        LoginPage loginPage = new LoginPage();
        loginPage.visit();

        // Declarative - what it means to log in is elsewhere;
        // Not data providers or ordered params, the object
        loginPage.loginSuccessfully(userData);

        Assert.assertFalse(loginPage.isOnPage());
    }

    @Test
    public void loginFailure() {
        // Use contextually informative static methods to create Data Objects
        UserData userData = UserData.valid();

        // Don't put the magic in the constructor, have a method that does what you expect it to
        LoginPage loginPage = new LoginPage();
        loginPage.visit();

        // Declarative - what it means to log in is elsewhere;
        // No data providers or ordered params, just an object
        loginPage.loginSuccessfully(userData);

        Assert.assertFalse(loginPage.isOnPage());
    }
}
