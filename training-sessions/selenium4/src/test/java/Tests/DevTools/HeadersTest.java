package Tests.DevTools;

import com.google.common.collect.ImmutableMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v86.network.model.BlockedReason;
import org.openqa.selenium.devtools.v86.network.model.Headers;
import org.openqa.selenium.devtools.v86.network.model.ResourceType;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.devtools.v86.network.Network.*;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
public class HeadersTest {
    private ChromeDriver chromeDriver;
    private DevTools devTools;

    @Before
    public void setUp() {
        chromeDriver = new ChromeDriver();
        devTools = chromeDriver.getDevTools();
        devTools.createSession();
    }

    @Test
    public void basicAuthentication() {
        devTools.send(new Command<>("Network.enable", new HashMap<>()));

        String auth = "admin:admin";
        String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());

        Map<String, Object> authorization = ImmutableMap.of("Authorization", "Basic " + encodeToString);
        devTools.send(setExtraHTTPHeaders(new Headers(authorization)));

        chromeDriver.get("https://the-internet.herokuapp.com/basic_auth");
        String text = chromeDriver.findElement(By.tagName("p")).getText();
        Assert.assertEquals("Congratulations! You must have the proper credentials.", text);

        chromeDriver.quit();
    }

    @Test
    public void addExtraHeader() {
        // https://github.com/diemol/selenium-4-demo/blob/master/src/test/java/CDPTest.java

        chromeDriver.get("https://www.whatismybrowser.com/detect/what-http-headers-is-my-browser-sending");

        devTools.send(new Command<>("Network.enable", new HashMap<>()));
        devTools.send(setExtraHTTPHeaders(new Headers(ImmutableMap.of("AAAA", "BBBB"))));

        devTools.addListener(loadingFailed(), loadingFailed -> {
            if (loadingFailed.getType().equals(ResourceType.STYLESHEET)) {
                assertEquals(loadingFailed.getBlockedReason(), BlockedReason.INSPECTOR);
            }
        });

        devTools.addListener(requestWillBeSent(),
                requestWillBeSent ->
                        assertEquals(requestWillBeSent.getRequest().getHeaders().get("meetup"), "TestingUY"));

        devTools.addListener(dataReceived(),
                dataReceived -> Assert.assertNotNull(dataReceived.getRequestId()));

        chromeDriver.get("https://www.whatismybrowser.com/detect/what-http-headers-is-my-browser-sending");

        Assert.assertEquals("AAAA", chromeDriver.findElement(By.tagName("th")).getText());
        Assert.assertEquals("BBBB", chromeDriver.findElement(By.tagName("td")).getText());

        chromeDriver.quit();
    }
}