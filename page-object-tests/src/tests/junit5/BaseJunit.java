package tests.junit5;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;


public class BaseJunit {
    protected WebDriver driver;


    @BeforeAll
    static void setup(){
        System.out.println("@BeforeAll executed\n");
    }

    @BeforeEach
    public void setupThis() throws MalformedURLException {
        // This automatically uses 2.38 and is windows/mac agnostic
        System.setProperty("wdm.chromeDriverVersion", "2.38");
        System.setProperty("wdm.targetPath", "lib/drivers/auto/");
        ChromeDriverManager.getInstance().setup();

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");

        driver = new ChromeDriver(chromeOptions);
        System.out.println("@BeforeEach executed\n");
    }

    @AfterEach
    public void teardown() {
        driver.quit();
        System.out.println("@AfterEach executed\n");
    }

    @AfterAll
    static void tear(){
        System.out.println("@AfterAll executed\n");
    }
}


