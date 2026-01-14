package com.saucelabs.extensions;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.saucelabs.bindings.SaucePlaywrightSession;
import com.saucelabs.saucebindings.CITools;
import com.saucelabs.saucebindings.DataCenter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestWatcher;

public class SaucePlaywrightExtension
    implements TestWatcher, BeforeEachCallback, ParameterResolver {
  private static final Logger LOGGER = Logger.getLogger(SaucePlaywrightExtension.class.getName());
  private static final String BROWSER_NAME = System.getProperty("sauce.browser", "Chrome");
  protected DataCenter dataCenter;

  public SaucePlaywrightExtension() {
    this(DataCenter.US_WEST);
  }

  private SaucePlaywrightExtension(DataCenter dataCenter) {
    this.dataCenter = dataCenter;
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    SaucePlaywrightSession session = new SaucePlaywrightSession(createOptions(context));
    session.setDataCenter(dataCenter);
    Browser browser = session.start();

    Browser.NewContextOptions newContextOptions =
        new Browser.NewContextOptions().setViewportSize(null);
    Page page = browser.newContext(newContextOptions).newPage();

    getStore(context).put("session", session);
    getStore(context).put("browser", browser);
    getStore(context).put("page", page);
  }

  public Map<String, Object> createOptions(ExtensionContext context) {
    Map<String, Object> sauceOptions = new HashMap<>();
    sauceOptions.put("username", System.getenv("SAUCE_USERNAME"));
    sauceOptions.put("accessKey", System.getenv("SAUCE_ACCESS_KEY"));
    sauceOptions.put("devTools", true);
    sauceOptions.put("_tptCommanderVersion", "stable");
    sauceOptions.put("name", getTestName(context));
    sauceOptions.put("build", CITools.getBuildName() + ": " + CITools.getBuildNumber());

    Map<String, Object> sessionRequest = new HashMap<>();
    sessionRequest.put("platformName", "macOS 13");
    sessionRequest.put("browserName", BROWSER_NAME);
    sessionRequest.put("sauce:options", sauceOptions);

    Map<String, Object> capabilities = new HashMap<>();
    capabilities.put("alwaysMatch", sessionRequest);

    Map<String, Object> payload = new HashMap<>();
    payload.put("capabilities", capabilities);

    return payload;
  }

  private String getTestName(ExtensionContext context) {
    // Use value specified by @DisplayName annotation if present
    if (context.getRequiredTestMethod().getDeclaredAnnotation(DisplayName.class) != null) {
      return context.getDisplayName();
    } else {
      String className = context.getRequiredTestClass().getSimpleName();
      String methodName = context.getRequiredTestMethod().getName();
      return className + ": " + methodName;
    }
  }

  private ExtensionContext.Store getStore(ExtensionContext context) {
    return context.getStore(
        ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
  }

  @Override
  public void testSuccessful(ExtensionContext context) {
    SaucePlaywrightSession session = (SaucePlaywrightSession) getStore(context).get("session");
    try {
      session.stop(true);
    } catch (Exception e) {
      LOGGER.severe("Session quit prematurely; Allow SaucePlaywrightExtension to stop the test");
      throw e;
    }
  }

  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    SaucePlaywrightSession session = (SaucePlaywrightSession) getStore(context).get("session");
    if (session != null) {
      try {
        session.stop(false);
      } catch (Exception e) {
        LOGGER.severe("Session quit prematurely; Allow SaucePlaywrightExtension to stop the test");
        throw e;
      }
    }
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext) {
    boolean session = parameterContext.getParameter().getType() == SaucePlaywrightSession.class;
    boolean browser = parameterContext.getParameter().getType() == Browser.class;
    boolean page = parameterContext.getParameter().getType() == Page.class;
    return session || browser || page;
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext) {
    if (parameterContext.getParameter().getType() == Browser.class) {
      return getStore(extensionContext).get("browser");
    } else if (parameterContext.getParameter().getType() == SaucePlaywrightSession.class) {
      return getStore(extensionContext).get("session");
    } else if (parameterContext.getParameter().getType() == Page.class) {
      return getStore(extensionContext).get("page");
    } else {
      throw new RuntimeException("Only browser, session and page instances are supported");
    }
  }
}
