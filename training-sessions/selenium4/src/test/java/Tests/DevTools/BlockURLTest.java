package Tests.DevTools;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v86.network.Network;
import org.openqa.selenium.devtools.v86.network.model.BlockedReason;
import org.openqa.selenium.devtools.v86.network.model.ResourceType;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.devtools.v86.network.Network.setBlockedURLs;

@SuppressWarnings("ALL")
public class BlockURLTest {

    @Test
    public void blockUrls() {
        // https://github.com/diemol/selenium-4-demo/blob/master/src/test/java/CDPTest.java

        ChromeDriver chromeDriver = new ChromeDriver();
        DevTools devTools = chromeDriver.getDevTools();
        devTools.createSession();

        // Network enabled
        devTools.send(new Command<>("Network.enable", new HashMap<>()));

        // Block urls that have png and css
        devTools.send(setBlockedURLs(ImmutableList.of("*.css", "*.png")));

        // Listening to events and check that the urls are actually blocked
        devTools.addListener(Network.loadingFailed(), loadingFailed -> {
            if (loadingFailed.getType().equals(ResourceType.STYLESHEET) ||
                    loadingFailed.getType().equals(ResourceType.IMAGE)) {
                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.INSPECTOR);
            }
        });

        chromeDriver.get("https://www.saucedemo.com");

        chromeDriver.quit();
    }
}