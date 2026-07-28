package com.saucelabs;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Base64;
import java.util.regex.Pattern;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;

public class StandaloneTest {

  static final String SAUCE_USERNAME = System.getenv("SAUCE_USERNAME");
  static final String SAUCE_ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
  static final String SAUCE_URL = "https://ondemand.us-west-1.saucelabs.com/wd/hub/";
  static final String SAUCE_API_URL = "https://api.us-west-1.saucelabs.com";
  static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
  static APIRequestContext request;
  static Playwright playwright;
  @RegisterExtension public SauceTestWatcher watcher = new SauceTestWatcher();
  Browser browser;
  BrowserContext context;
  Page page;
  String sessionId;
  String cdpEndpoint;
  TestInfo testInfo;

  @BeforeAll
  static void beforeAll() {
    createPlaywright();
    createAPIRequestContext();
  }

  static void closePlaywright() {
    if (playwright != null) {
      playwright.close();
      playwright = null;
    }
  }

  @AfterAll
  static void afterAll() {
    closePlaywright();
  }

  static void createAPIRequestContext() {
    request =
        playwright.request().newContext(new APIRequest.NewContextOptions().setBaseURL(SAUCE_URL));
  }

  static void createPlaywright() {
    playwright = Playwright.create();
  }

  private static JsonObject getSessionPayload() {
    JsonObject sauceOptions = new JsonObject();
    sauceOptions.addProperty("username", SAUCE_USERNAME);
    sauceOptions.addProperty("accessKey", SAUCE_ACCESS_KEY);
    sauceOptions.addProperty("devTools", Boolean.TRUE);
    sauceOptions.addProperty("_tptCommanderVersion", "stable");

    JsonObject sessionRequest = new JsonObject();
    sessionRequest.addProperty("platformName", "macOS 13");
    sessionRequest.addProperty("browserName", "Chrome");
    sessionRequest.add("sauce:options", sauceOptions);

    JsonObject capabilities = new JsonObject();
    capabilities.add("alwaysMatch", sessionRequest);
    JsonObject payload = new JsonObject();
    payload.add("capabilities", capabilities);
    return payload;
  }

  void createSession() {
    JsonObject payload = getSessionPayload();

    APIResponse newSessionResponse =
        request.fetch(
            "session",
            RequestOptions.create()
                .setMethod("POST")
                .setData(payload.toString())
                .setMaxRedirects(5)
                .setTimeout(120000));

    JsonObject newSessionBlob = new Gson().fromJson(newSessionResponse.text(), JsonObject.class);
    sessionId = newSessionBlob.get("value").getAsJsonObject().get("sessionId").getAsString();
    cdpEndpoint =
        newSessionBlob
            .get("value")
            .getAsJsonObject()
            .get("capabilities")
            .getAsJsonObject()
            .get("se:cdp")
            .getAsString();
  }

  @BeforeEach
  void launchBrowserAndCreatePage(TestInfo testInfo) throws IOException {
    createSession();
    this.testInfo = testInfo;
    JsonObject renamePayload = new JsonObject();
    renamePayload.addProperty("name", testInfo.getDisplayName());
    updateJob(sessionId, renamePayload);
    browser = playwright.chromium().connectOverCDP(cdpEndpoint);

    // Maximize browser
    Browser.NewContextOptions newContextOptions =
        new Browser.NewContextOptions().setViewportSize(null);
    context = browser.newContext(newContextOptions);
    page = context.newPage();
  }

  @AfterEach
  void closeContextAndWindow() {
    context.close();
    browser.close();
    request.delete("session/" + sessionId);
  }

  static void updateJob(String sessionId, JsonObject payload) throws IOException {
    String credentials = SAUCE_USERNAME + ":" + SAUCE_ACCESS_KEY;
    String authHeader =
        "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

    HttpRequest httpRequest =
        HttpRequest.newBuilder(
                URI.create(SAUCE_API_URL + "/rest/v1/" + SAUCE_USERNAME + "/jobs/" + sessionId))
            .timeout(Duration.ofSeconds(30))
            .header("Authorization", authHeader)
            .header("Content-Type", "application/json")
            .method("PUT", HttpRequest.BodyPublishers.ofString(payload.toString()))
            .build();
    try {
      HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.discarding());
    } catch (InterruptedException e) {
      throw new IOException(e);
    }
  }

  @Test
  void shouldClickButton() {
    page.navigate(
        "data:text/html,<script>var result;</script><button "
            + "onclick='result=\"Clicked\"'>Go</button>");
    page.locator("button").click();
    assertEquals("Clicked", page.evaluate("result"));
  }

  @Test
  void shouldCheckTheBox() {
    page.setContent("<input id='checkbox' type='checkbox'></input>");
    page.locator("input").check();
    assertTrue((Boolean) page.evaluate("() => window['checkbox'].checked"));
  }

  @Test
  void shouldSearchWiki() {
    page.navigate("https://www.wikipedia.org/");
    page.locator("input[name=\"search\"]").click();
    page.locator("input[name=\"search\"]").fill("playwright");
    page.locator("input[name=\"search\"]").press("Enter");
    assertEquals("https://en.wikipedia.org/wiki/Playwright", page.url());
  }

  @Test
  void shouldTakeScreenshot() {
    page.navigate("https://playwright.dev");
    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
    assertThat(page).hasTitle(Pattern.compile("Playwright"));
  }

  public class SauceTestWatcher implements TestWatcher {
    @Override
    public void testSuccessful(ExtensionContext context) {
      printResults();
      reportResult(true);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
      printResults();
      reportResult(false);
    }

    private void reportResult(boolean passed) {
      JsonObject resultPayload = new JsonObject();
      resultPayload.addProperty("passed", passed);
      try {
        updateJob(sessionId, resultPayload);
      } catch (IOException e) {
        System.out.println("Problem setting job result: " + e);
      }
    }

    public void printResults() {
      String sauceReporter =
          String.format(
              "SauceOnDemandSessionID=%s job-name=%s", sessionId, testInfo.getDisplayName());
      String sauceLink = String.format("Job Link: https://app.saucelabs.com/tests/%s", sessionId);
      System.out.print(sauceReporter + "\n" + sauceLink + "\n");
    }
  }
}
