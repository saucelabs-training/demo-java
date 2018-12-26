package tests.testng;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTestNG {

    protected WebDriver driver;

    @BeforeSuite
    static void setup() {
    }

    @BeforeMethod
    public void setupThis() {
        // This automatically uses 2.38 and is windows/mac agnostic
        System.setProperty("wdm.chromeDriverVersion", "2.38");
        System.setProperty("wdm.targetPath", "lib/drivers/auto/");
        ChromeDriverManager.getInstance().setup();

        //Set new instance of ChromeOptions
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");

        //Pass ChromeOptions to WebDriver Instance
        driver = new ChromeDriver(chromeOptions);
        System.out.println("@BeforeTest executed\n");
    }

    @AfterMethod
    public void teardown() {
        //Tear Down WebDriver Instance
        driver.quit();
        System.out.println("@AfterTest executed\n");
    }

    @AfterSuite
    static void tear(){
        System.out.println("@AfterSuite executed\n");
    }
}


