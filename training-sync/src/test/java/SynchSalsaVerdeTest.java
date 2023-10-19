import com.saucelabs.salsaverde.Browser;
import com.saucelabs.salsaverde.elements.Element;
import com.saucelabs.salsaverde.exceptions.ElementNotEnabledException;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SynchSalsaVerdeTest {
    public SauceSession session;
    public Browser browser;

    @Rule
    public TestName name = new TestName();

    @Before
    public void setup() {
        SauceOptions options = new SauceOptions();
        options.setName(name.getMethodName());
        session = new SauceSession(options);
        RemoteWebDriver driver = session.start();
        browser = new Browser(driver);
    }

    @Test
    public void synchronizeSalsaVerde() {
        browser.goTo("http://watir.com/examples/wait.html");

        browser.element(By.id("add_foobar")).click();

        try {
            browser.element(By.id("foobar")).click();
            session.stop(true);
        } catch (ElementNotInteractableException e) {
            session.stop(false);
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void fillFormSalsaVerde() {
        browser.goTo("http://watir.com/examples/simple_form.html");

        browser.element(By.id("new_user_first_name")).setText("First");
        browser.element(By.id("new_user_last_name")).setText("Last");
        browser.element(By.id("new_user_email")).setText("user@example.com");
        browser.element(By.id("submitButton")).click();

        boolean result = !("http://watir.com/examples/simple_form.html").equals(browser.getCurrentUrl());

        session.stop(result);
    }
}
