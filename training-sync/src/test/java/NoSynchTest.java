import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.RemoteWebDriver;


public class NoSynchTest {
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

    @Test
    public void failsToSynch() {
        driver.get("http://watir.com/examples/wait.html");

        driver.findElement(By.id("add_foobar")).click();

        try {
            driver.findElement(By.id("foobar")).click();
            session.stop(true);
        } catch (NoSuchElementException e) {
            session.stop(false);
            Assert.assertTrue(e.getMessage(), false);
        }
    }
}
