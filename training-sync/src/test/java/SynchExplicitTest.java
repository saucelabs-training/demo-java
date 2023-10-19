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


public class SynchExplicitTest {
    private SauceSession session;
    private RemoteWebDriver driver;

    @Rule
    public TestName name = new TestName();

    @Before
    public void setup() {
        SauceOptions options = new SauceOptions();
        options.setName(name.getMethodName());
        session = new SauceSession(options);
        driver = session.start();
    }

    @Test
    public void synchronizeExplicit() {
        driver.get("http://watir.com/examples/wait.html");
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add_foobar")));
        driver.findElement(By.id("add_foobar")).click();

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("foobar")));

        try {
            element.click();
            session.stop(true);
        } catch (ElementNotInteractableException e) {
            session.stop(false);
            Assert.assertTrue(e.getMessage(), false);
        }
    }
}
