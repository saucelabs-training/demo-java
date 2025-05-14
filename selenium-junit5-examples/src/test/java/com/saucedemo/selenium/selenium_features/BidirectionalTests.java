package com.saucedemo.selenium.selenium_features;

import com.saucedemo.selenium.TestBase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.bidi.browsingcontext.CaptureScreenshotParameters;
import org.openqa.selenium.bidi.log.ConsoleLogEntry;
import org.openqa.selenium.bidi.log.JavascriptLogEntry;
import org.openqa.selenium.bidi.module.LogInspector;
import org.openqa.selenium.bidi.module.Network;
import org.openqa.selenium.bidi.network.AddInterceptParameters;
import org.openqa.selenium.bidi.network.ContinueRequestParameters;
import org.openqa.selenium.bidi.network.Header;
import org.openqa.selenium.bidi.network.InterceptPhase;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BidirectionalTests extends TestBase {

  WebDriverWait wait;

  @BeforeEach
  public void setup(TestInfo testInfo) {
    Map<String, Object> sauceOptions = defaultSauceOptions(testInfo);
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.enableBiDi();
    startSession(firefoxOptions, sauceOptions);

    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver = new Augmenter().augment(driver);
  }

  @Test
  void consoleLogs() throws ExecutionException, InterruptedException, TimeoutException {
    try (LogInspector logInspector = new LogInspector(driver)) {
      CompletableFuture<ConsoleLogEntry> future = new CompletableFuture<>();
      logInspector.onConsoleEntry(future::complete);

      driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
      driver.findElement(By.id("consoleLog")).click();

      ConsoleLogEntry logEntry = future.get(5, TimeUnit.SECONDS);

      Assertions.assertEquals("Hello, world!", logEntry.getText());
    }
  }

  @Test
  void jsErrors() throws ExecutionException, InterruptedException, TimeoutException {
    try (LogInspector logInspector = new LogInspector(driver)) {
      CompletableFuture<JavascriptLogEntry> future = new CompletableFuture<>();
      logInspector.onJavaScriptException(future::complete);

      driver.get("https://www.selenium.dev/selenium/web/bidi/logEntryAdded.html");
      driver.findElement(By.id("jsException")).click();

      JavascriptLogEntry logEntry = future.get(5, TimeUnit.SECONDS);

      Assertions.assertEquals("Error: Not working", logEntry.getText());
      Assertions.assertEquals("javascript", logEntry.getType());
    }
  }

  @Test
  void basicAuth() {
    try (Network network = new Network(driver)) {
      network.addIntercept(new AddInterceptParameters(InterceptPhase.AUTH_REQUIRED));
      network.onAuthRequired(
          responseDetails ->
              network.continueWithAuth(
                  responseDetails.getRequest().getRequestId(),
                  new UsernameAndPassword("admin", "admin")));
      driver.get("https://the-internet.herokuapp.com/basic_auth");
      String successMessage = "Congratulations! You must have the proper credentials.";
      WebElement elementMessage = driver.findElement(By.tagName("p"));
      Assertions.assertEquals(successMessage, elementMessage.getText());
    }
  }

  @Test
  public void fullPageScreenshot() throws IOException {
    Path fullPageScreenshot = Paths.get("FullPage.png");
    if (fullPageScreenshot.toFile().exists()) {
      fullPageScreenshot.toFile().delete();
    }
    BrowsingContext browsingContext = new BrowsingContext(driver, driver.getWindowHandle());
    driver.get("https://www.saucedemo.com/v1/inventory.html");
    CaptureScreenshotParameters csp = new CaptureScreenshotParameters();
    csp.origin(CaptureScreenshotParameters.Origin.DOCUMENT);
    String screenshot = browsingContext.captureScreenshot(csp);
    byte[] screenshotAs = OutputType.BYTES.convertFromBase64Png(screenshot);
    Files.write(fullPageScreenshot, screenshotAs);
    Assertions.assertTrue(fullPageScreenshot.toFile().exists());
  }

  @Test
  void recordResponse() {
    CopyOnWriteArrayList<String> contentType = new CopyOnWriteArrayList<>();
    try (Network network = new Network(driver)) {
      network.addIntercept(new AddInterceptParameters(InterceptPhase.BEFORE_REQUEST_SENT));
      network.onBeforeRequestSent(
          beforeRequestSent -> {
            Optional<Header> maybeContentType =
                beforeRequestSent.getRequest().getHeaders().stream()
                    .filter(header -> header.getName().equalsIgnoreCase("Host"))
                    .findFirst();
            maybeContentType.ifPresent(header -> contentType.add(header.getValue().getValue()));
            network.continueRequest(
                new ContinueRequestParameters(beforeRequestSent.getRequest().getRequestId()));
          });
      driver.get("https://www.selenium.dev/selenium/web/blank.html");
      wait.until(_d -> contentType.size() > 1);
      Assertions.assertEquals("www.selenium.dev", contentType.get(0));
    }
  }
}
