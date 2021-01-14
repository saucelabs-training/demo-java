package Tests.DevTools;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v84.log.Log;

public class ConsoleLogsTest {

    @Test
    public void consoleLogs() {
        // https://crazyautomator.com/reading-console-logs-of-chrome-browser-using-selenium-4-chrome-devtools-and-java/

        ChromeDriver chromeDriver = new ChromeDriver();
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), entry -> System.out.println(entry.getText()));
        chromeDriver.get("https://nhnb.github.io/console-log/console-log/demo.html");

        chromeDriver.quit();
    }

}