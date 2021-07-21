import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LocalExecutionTest {

    @Test
    public void localExecution() {
        // Options:
        //
        // 1. Specify location of driver
        // System.setProperty("webdriver.chrome.driver", "lib/drivers/chromedriver");
        //
        // 2. Add driver to PATH ENV
        //
        // 3. Use Driver manager:
        WebDriverManager.chromedriver().setup();

        // Start session (opens browser)
        RemoteWebDriver driver = new ChromeDriver();

        // Quit session (closes browser)
        driver.quit();
    }
}
