package com.saucelabs.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import org.json.JSONObject;

/**
 * A single Sauce Labs job created through the native Playwright WebSocket endpoint
 * (POST /playwright/session), as opposed to the old WebDriver-session + CDP approach. Unlike CDP,
 * this endpoint's connection is a plain WebSocket, so it drives Chromium, Firefox and WebKit
 * alike.
 */
public class SauceSession {
  private static final Duration VM_PREP_TIMEOUT = Duration.ofSeconds(120);
  private static final HttpClient HTTP_CLIENT =
      HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NEVER).build();

  private final SauceRegion region;
  private final String username;
  private final String accessKey;
  private final String sessionId;
  private final String wsEndpoint;
  private final String sessionName;

  private SauceSession(
      SauceRegion region,
      String username,
      String accessKey,
      String sessionId,
      String wsEndpoint,
      String sessionName) {
    this.region = region;
    this.username = username;
    this.accessKey = accessKey;
    this.sessionId = sessionId;
    this.wsEndpoint = wsEndpoint;
    this.sessionName = sessionName;
  }

  public static SauceSession create(
      SauceRegion region,
      String username,
      String accessKey,
      String browserName,
      String sessionName) {
    String build = BuildInfo.BUILD_NAME + ": " + BuildInfo.BUILD_NUMBER;
    JSONObject payload =
        new JSONObject()
            .put("browserName", browserName)
            .put("platformName", "Linux")
            .put("playwrightVersion", installedPlaywrightVersion())
            .put(
                "sauce:options", new JSONObject().put("name", sessionName).put("build", build));

    JSONObject response =
        requestFollowing303s(
            region.ondemandUrl() + "/playwright/session", username, accessKey, payload);
    JSONObject value = response.has("value") ? response.getJSONObject("value") : response;

    return new SauceSession(
        region,
        username,
        accessKey,
        value.getString("sessionId"),
        value.getString("wsEndpoint"),
        sessionName);
  }

  private static JSONObject requestFollowing303s(
      String url, String username, String accessKey, JSONObject payload) {
    String authHeader = basicAuthHeader(username, accessKey);
    String body = payload.toString();
    String nextUrl = url;
    String method = "POST";
    String nextBody = body;

    try {
      while (true) {
        HttpRequest.Builder builder =
            HttpRequest.newBuilder(URI.create(nextUrl))
                .timeout(VM_PREP_TIMEOUT)
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json");
        builder =
            "POST".equals(method)
                ? builder.POST(HttpRequest.BodyPublishers.ofString(nextBody))
                : builder.GET();

        HttpResponse<String> response =
            HTTP_CLIENT.send(builder.build(), HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 303) {
          if (response.statusCode() >= 400) {
            throw new SauceSessionException(
                "Sauce session creation failed with status "
                    + response.statusCode()
                    + ": "
                    + response.body());
          }
          return new JSONObject(response.body());
        }

        String location =
            response
                .headers()
                .firstValue("Location")
                .orElseThrow(
                    () ->
                        new SauceSessionException(
                            "Sauce responded 303 without a Location header"));
        nextUrl = location.startsWith("http") ? location : (region(url) + location);
        method = "GET";
      }
    } catch (IOException | InterruptedException e) {
      throw new SauceSessionException("Failed to create Sauce Playwright session", e);
    }
  }

  private static String region(String originalUrl) {
    URI uri = URI.create(originalUrl);
    return uri.getScheme() + "://" + uri.getAuthority();
  }

  private static String basicAuthHeader(String username, String accessKey) {
    String credentials = username + ":" + accessKey;
    return "Basic "
        + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
  }

  private static String installedPlaywrightVersion() {
    String version = Playwright.class.getPackage().getImplementationVersion();
    if (version == null) {
      throw new SauceSessionException(
          "Could not read the installed Playwright version from the JAR manifest");
    }
    int secondDot = version.indexOf('.', version.indexOf('.') + 1);
    return secondDot == -1 ? version : version.substring(0, secondDot);
  }

  public Browser connect(Playwright playwright, String browserName) {
    BrowserType browserType = browserTypeFor(playwright, browserName);
    return browserType.connect(wsEndpoint + "?browser=" + browserName);
  }

  private static BrowserType browserTypeFor(Playwright playwright, String browserName) {
    switch (browserName) {
      case "firefox":
        return playwright.firefox();
      case "webkit":
        return playwright.webkit();
      case "chromium":
        return playwright.chromium();
      default:
        throw new SauceSessionException("Unsupported browser name: " + browserName);
    }
  }

  public void reportResult(boolean passed) {
    String url = region.apiUrl() + "/rest/v1/" + username + "/jobs/" + sessionId;
    JSONObject payload = new JSONObject().put("passed", passed);

    try {
      HttpRequest request =
          HttpRequest.newBuilder(URI.create(url))
              .timeout(Duration.ofSeconds(30))
              .header("Authorization", basicAuthHeader(username, accessKey))
              .header("Content-Type", "application/json")
              .method("PUT", HttpRequest.BodyPublishers.ofString(payload.toString()))
              .build();
      HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
    } catch (IOException | InterruptedException e) {
      throw new SauceSessionException("Failed to report result for Sauce session " + sessionId, e);
    }
  }

  public void printJobLink() {
    // Add output for the Sauce OnDemand Jenkins plugin
    // The first print statement will automatically populate links on Jenkins to Sauce
    // The second print statement will output the job link to logging/console
    System.out.printf("SauceOnDemandSessionID=%s job-name=%s%n", sessionId, sessionName);
    System.out.printf("Test Job Link: %s%s%n", region.testLink(), sessionId);
  }

  public static class SauceSessionException extends RuntimeException {
    public SauceSessionException(String message) {
      super(message);
    }

    public SauceSessionException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
