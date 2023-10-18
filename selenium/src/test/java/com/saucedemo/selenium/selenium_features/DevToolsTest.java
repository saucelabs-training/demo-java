package com.saucedemo.selenium.selenium_features;

import static org.openqa.selenium.devtools.events.CdpEventTypes.consoleEvent;
import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;

import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;
import com.saucedemo.selenium.TestBase;
import java.net.URI;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v118.browser.Browser;
import org.openqa.selenium.devtools.v118.network.Network;
import org.openqa.selenium.devtools.v118.network.model.Headers;
import org.openqa.selenium.devtools.v118.performance.Performance;
import org.openqa.selenium.devtools.v118.performance.model.Metric;
import org.openqa.selenium.devtools.v118.runtime.Runtime;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.Filter;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DevToolsTest extends TestBase {
  WebDriverWait wait;

  @BeforeEach
  public void setup(TestInfo testInfo) {
    Map<String, Object> sauceOptions = new HashMap<>();
    sauceOptions.put("devtools", true);
    startSession(testInfo, new ChromeOptions(), sauceOptions);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver = new Augmenter().augment(driver);
  }

  @Test
  public void setCookie() {
    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();

    devTools.send(
        Network.setCookie(
            "cheese",
            "gouda",
            Optional.empty(),
            Optional.of("www.selenium.dev"),
            Optional.empty(),
            Optional.of(true),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()));

    driver.get("https://www.selenium.dev");
    Cookie cheese = driver.manage().getCookieNamed("cheese");
    Assertions.assertEquals("gouda", cheese.getValue());
  }

  @Test
  public void performanceMetrics() {
    driver.get("https://www.selenium.dev/selenium/web/frameset.html");

    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Performance.enable(Optional.empty()));

    List<Metric> metricList = devTools.send(Performance.getMetrics());

    Map<String, Number> metrics = new HashMap<>();
    for (Metric metric : metricList) {
      metrics.put(metric.getName(), metric.getValue());
    }

    Assertions.assertTrue(metrics.get("DevToolsCommandDuration").doubleValue() > 0);
    Assertions.assertEquals(12, metrics.get("Frames").intValue());
  }

  @Test
  public void basicAuthenticationCdpApi() {
    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));

    String encodedAuth = Base64.getEncoder().encodeToString("admin:admin".getBytes());
    Map<String, Object> headers = ImmutableMap.of("Authorization", "Basic " + encodedAuth);

    devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

    driver.get("https://the-internet.herokuapp.com/basic_auth");

    Assertions.assertEquals(
        "Congratulations! You must have the proper credentials.",
        driver.findElement(By.tagName("p")).getText());
  }

  @Test
  public void consoleLogs() {
    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Runtime.enable());

    CopyOnWriteArrayList<String> logs = new CopyOnWriteArrayList<>();
    devTools.addListener(
        Runtime.consoleAPICalled(),
        event -> logs.add((String) event.getArgs().get(0).getValue().orElse("")));

    driver.findElement(By.id("consoleLog")).click();

    wait.until(_d -> !logs.isEmpty());
    Assertions.assertEquals("Hello, world!", logs.get(0));
  }

  @Test
  public void jsErrors() {
    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(Runtime.enable());

    CopyOnWriteArrayList<JavascriptException> errors = new CopyOnWriteArrayList<>();
    devTools.getDomains().events().addJavascriptExceptionListener(errors::add);

    driver.findElement(By.id("jsException")).click();

    wait.until(_d -> !errors.isEmpty());
    Assertions.assertTrue(errors.get(0).getMessage().contains("Error: Not working"));
  }

  @Test
  public void waitForDownload() {
    driver.get("https://www.selenium.dev/selenium/web/downloads/download.html");

    DevTools devTools = ((HasDevTools) driver).getDevTools();
    devTools.createSession();
    devTools.send(
        Browser.setDownloadBehavior(
            Browser.SetDownloadBehaviorBehavior.ALLOWANDNAME,
            Optional.empty(),
            Optional.of(""),
            Optional.of(true)));

    AtomicBoolean completed = new AtomicBoolean(false);
    devTools.addListener(
        Browser.downloadProgress(),
        e -> completed.set(Objects.equals(e.getState().toString(), "completed")));

    driver.findElement(By.id("file-2")).click();

    Assertions.assertDoesNotThrow(() -> wait.until(_d -> completed));
  }

  @Test
  @Disabled("For some reason, this one does not work")
  public void basicAuthenticationBidiApi() {
    Predicate<URI> uriPredicate = uri -> uri.toString().contains("herokuapp.com");
    Supplier<Credentials> authentication = UsernameAndPassword.of("admin", "admin");

    ((HasAuthentication) driver).register(uriPredicate, authentication);

    driver.get("https://the-internet.herokuapp.com/basic_auth");

    String successMessage = "Congratulations! You must have the proper credentials.";
    WebElement elementMessage = driver.findElement(By.tagName("p"));
    Assertions.assertEquals(successMessage, elementMessage.getText());
  }

  @Test
  public void mutatedElements() {
    driver.get("https://www.selenium.dev/selenium/web/dynamic.html");

    CopyOnWriteArrayList<WebElement> mutations = new CopyOnWriteArrayList<>();
    ((HasLogEvents) driver).onLogEvent(domMutation(e -> mutations.add(e.getElement())));

    driver.findElement(By.id("reveal")).click();

    wait.until(_d -> !mutations.isEmpty());
    Assertions.assertEquals(mutations.get(0), driver.findElement(By.id("revealed")));
  }

  @Test
  public void consoleLogsBidiApi() {
    driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");

    CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();
    ((HasLogEvents) driver).onLogEvent(consoleEvent(e -> messages.add(e.getMessages().get(0))));

    driver.findElement(By.id("consoleLog")).click();
    driver.findElement(By.id("consoleError")).click();

    wait.until(_d -> messages.size() > 1);
    Assertions.assertTrue(messages.contains("Hello, world!"));
    Assertions.assertTrue(messages.contains("I am console error"));
  }

  @Test
  public void recordResponse() {
    CopyOnWriteArrayList<String> contentType = new CopyOnWriteArrayList<>();

    try (NetworkInterceptor ignored =
        new NetworkInterceptor(
            driver,
            (Filter)
                next ->
                    req -> {
                      HttpResponse res = next.execute(req);
                      contentType.add(res.getHeader("Content-Type"));
                      return res;
                    })) {
      driver.get("https://www.selenium.dev/selenium/web/blank.html");
      wait.until(_d -> contentType.size() > 1);
    }

    Assertions.assertEquals("text/html; charset=utf-8", contentType.get(0));
  }

  @Test
  public void transformResponses() {
    try (NetworkInterceptor ignored =
        new NetworkInterceptor(
            driver,
            Route.matching(req -> true)
                .to(
                    () ->
                        req ->
                            new HttpResponse()
                                .setStatus(200)
                                .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
                                .setContent(Contents.utf8String("Creamy, delicious cheese!"))))) {

      driver.get("https://www.selenium.dev/selenium/web/blank.html");
    }

    WebElement body = driver.findElement(By.tagName("body"));
    Assertions.assertEquals("Creamy, delicious cheese!", body.getText());
  }
}
