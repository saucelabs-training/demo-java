package com.saucedemo.selenium.sauce_features;

import com.google.common.collect.ImmutableMap;
import com.saucedemo.selenium.TestBase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.HasDownloads;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.HasCdp;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.*;

public class NetworkCaptureTest extends TestBase {

  @Override
  protected void startSession(Capabilities options, Map<String, Object> sauceOptions) {
    sauceOptions.put("extendedDebugging", true);
    sauceOptions.put("networkCapture", true);
    super.startSession(options, sauceOptions);
  }

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
    driver = new Augmenter().augment(driver);
  }

  @Test
  public void setCookieCdpEndpoint() {
    Map<String, Object> cookie = new HashMap<>();
    cookie.put("name", "cheese");
    cookie.put("value", "gouda");
    cookie.put("domain", "www.selenium.dev");
    cookie.put("secure", true);

    ((HasCdp) driver).executeCdpCommand("Network.setCookie", cookie);

    driver.get("https://www.selenium.dev");
    Cookie cheese = driver.manage().getCookieNamed("cheese");
    Assertions.assertEquals("gouda", cheese.getValue());
  }

  @Test
  public void performanceMetricsCdpEndpoint() {
    driver.get("https://www.selenium.dev/selenium/web/frameset.html");

    ((HasCdp) driver).executeCdpCommand("Performance.enable", new HashMap<>());

    Map<String, Object> response =
            ((HasCdp) driver).executeCdpCommand("Performance.getMetrics", new HashMap<>());
    List<Map<String, Object>> metricList = (List<Map<String, Object>>) response.get("metrics");

    Map<String, Number> metrics = new HashMap<>();
    for (Map<String, Object> metric : metricList) {
      metrics.put((String) metric.get("name"), (Number) metric.get("value"));
    }

    Assertions.assertTrue(metrics.get("DevToolsCommandDuration").doubleValue() > 0);
    Assertions.assertEquals(12, metrics.get("Frames").intValue());
  }

  @Test
  public void basicAuthCdpEndpoint() {
    ((HasCdp) driver).executeCdpCommand("Network.enable", new HashMap<>());

    String encodedAuth = Base64.getEncoder().encodeToString("admin:admin".getBytes());
    Map<String, Object> headers =
            ImmutableMap.of("headers", ImmutableMap.of("authorization", "Basic " + encodedAuth));

    ((HasCdp) driver).executeCdpCommand("Network.setExtraHTTPHeaders", headers);

    driver.get("https://the-internet.herokuapp.com/basic_auth");

    Assertions.assertEquals(
            "Congratulations! You must have the proper credentials.",
            driver.findElement(By.tagName("p")).getText());
  }
}
