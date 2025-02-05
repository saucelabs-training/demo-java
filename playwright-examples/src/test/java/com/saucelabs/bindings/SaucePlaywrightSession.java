package com.saucelabs.bindings;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.saucelabs.saucebindings.DataCenter;
import com.saucelabs.saucebindings.SauceRest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.remote.SessionId;

public class SaucePlaywrightSession {
  @Getter protected Playwright playwright;
  @Getter @Setter private DataCenter dataCenter = DataCenter.US_WEST;
  private final Map<String, Object> capabilities;
  @Setter private URL sauceUrl;
  @Getter private Boolean result;
  private String id;
  private String cdpEndpoint;
  private SauceRest rest;
  private APIRequestContext request;
  private Browser browser;

  public SaucePlaywrightSession(Map<String, Object> capabilities) {
    this.capabilities = capabilities;
  }

  public Browser start() {
    this.browser = createPlaywrightSession();

    this.rest = sauceRest();
    return browser;
  }

  SauceRest sauceRest() {
    return new SauceRest(this.dataCenter, new SessionId(id));
  }

  private Browser createPlaywrightSession() {
    this.playwright = Playwright.create();
    this.request =
        playwright
            .request()
            .newContext(
                new APIRequest.NewContextOptions().setBaseURL(String.valueOf(getSauceUrl())));

    JSONObject jsonPayload = new JSONObject(capabilities);
    RequestOptions options = RequestOptions.create()
            .setMethod("POST")
            .setData(jsonPayload.toString())
            .setMaxRedirects(5)
            .setTimeout(120000);
    APIResponse newSessionResponse = request.fetch("session", options);
    JSONObject response = new JSONObject(newSessionResponse.text()).getJSONObject("value");

    this.id = response.getString("sessionId");
    this.cdpEndpoint = response.getJSONObject("capabilities").getString("se:cdp");

    return playwright.chromium().connectOverCDP(cdpEndpoint);
  }

  public void annotate(String comment) {
    RequestOptions options = RequestOptions.create().setData("{\"script\": \"sauce:context=" + comment + "\", \"args\": []}");

    request.post("session/" + id + "/execute/sync", options);
  }

  public  void addTag(String tag) {
    rest.addTags(Collections.singletonList(tag));
  }

  public URL getSauceUrl() {
    try {
      if (sauceUrl != null) {
        return sauceUrl;
      } else {
        return new URL(getDataCenter().getEndpoint() + "/");
      }
    } catch (MalformedURLException e) {
      throw new InvalidArgumentException("Invalid URL", e);
    }
  }

  public void stop(Boolean passed) {
    try {
      this.result = passed;
      printToConsole();

      rest.setResult(result);
      browser.close();
      request.delete("session/" + id);
      playwright.close();
    } catch (Exception e) {
      if (rest != null) {
        rest.stop();
      }
    } finally {
      browser = null;
      playwright = null;
    }
  }

  @SuppressWarnings("unchecked")
  private String getTestName() {
    Map<String, Object> caps = (Map<String, Object>) capabilities.get("capabilities");
    Map<String, Object> alwaysMatch = (Map<String, Object>) caps.get("alwaysMatch");
    Map<String, Object> sauceOptions = (Map<String, Object>) alwaysMatch.get("sauce:options");
    return sauceOptions.get("name").toString();
  }

  public void printToConsole() {
    // Add output for the Sauce OnDemand Jenkins plugin
    // The first print statement will automatically populate links on Jenkins to Sauce
    // The second print statement will output the job link to logging/console
    String sauceReporter =
        String.format("SauceOnDemandSessionID=%s job-name=%s", this.id, getTestName());
    String sauceTestLink =
        String.format("Test Job Link:" + getDataCenter().getTestLink() + "%s", this.id);
    System.out.print(sauceReporter + "\n" + sauceTestLink + "\n");
  }
}
