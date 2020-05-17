import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class    SynchAbstractTest {
    public SauceSession session;
    public RemoteWebDriver driver;

    @Rule
    public TestName name = new TestName();

    @Before
    public void setup() {
        SauceOptions options = new SauceOptions();
        options.setName(name.getMethodName());
        session = new SauceSession(options);
        driver = session.start();
    }

    public void click(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    public void sendKeys(By locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    @Test
    public void synchronizeAbstract() {
        driver.get("http://watir.com/examples/wait.html");

        click(By.id("add_foobar"));

        try {
            click(By.id("foobar"));
            session.stop(true);
        } catch (ElementNotInteractableException e) {
            session.stop(false);
            Assert.assertTrue(e.getMessage(), false);
        }
    }

    @Test
    public void fillFormAbstract() {
        driver.get("http://watir.com/examples/simple_form.html");

        sendKeys(By.id("new_user_first_name"), "First");
        sendKeys(By.id("new_user_last_name"), "Last");
        sendKeys(By.id("new_user_email"), "user@example.com");
        click(By.id("submitButton"));

        boolean result = !("http://watir.com/examples/simple_form.html").equals(driver.getCurrentUrl());

        session.stop(result);
    }
}
