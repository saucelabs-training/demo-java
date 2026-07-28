package com.saucelabs.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * Creates Sauce Playwright sessions through the native WebSocket endpoint and injects
 * Browser/Page/SauceSession into test methods.
 *
 * <p>Session grouping follows the same switch JUnit5's own parallel executor uses: {@code
 * junit.jupiter.execution.parallel.mode.default}. When it's {@code same_thread} (this project's
 * default), one session is shared across every method of a test class - JUnit5 schedules a
 * same_thread class's methods on a single thread with no overlap, confirmed empirically to never
 * interleave across classes even when the thread pool is smaller than the number of classes. When
 * it's {@code concurrent}, every test method gets its own independent session instead, since
 * methods of one class may then run on different threads at once and can't safely share a single
 * browser connection.
 *
 * <p>A session whose test fails is closed and reported immediately rather than handed to whatever
 * runs next in the same group - a session that just saw a failure isn't assumed clean.
 */
public class SauceExtension
    implements BeforeEachCallback, AfterEachCallback, AfterAllCallback, ParameterResolver {

  private static final ExtensionContext.Namespace NAMESPACE =
      ExtensionContext.Namespace.create(SauceExtension.class);
  private static final Object SESSION_KEY = new Object();
  private static final Object PAGE_KEY = new Object();

  private static final String BROWSER_NAME = System.getProperty("sauce.browser.name", "chromium");
  private static final SauceRegion REGION = SauceRegion.US_WEST;

  private static final class SessionHolder implements ExtensionContext.Store.CloseableResource {
    private final Playwright playwright;
    private final SauceSession session;
    private final Browser browser;
    private volatile boolean failed;

    SessionHolder(Playwright playwright, SauceSession session, Browser browser) {
      this.playwright = playwright;
      this.session = session;
      this.browser = browser;
    }

    @Override
    public void close() {
      session.reportResult(!failed);
      session.printJobLink();
      browser.close();
      playwright.close();
    }
  }

  @Override
  public void beforeEach(ExtensionContext context) {
    SessionHolder holder =
        sessionStore(context)
            .getOrComputeIfAbsent(SESSION_KEY, key -> createHolder(context), SessionHolder.class);

    Page page =
        holder.browser.newContext(new Browser.NewContextOptions().setViewportSize(null)).newPage();
    context.getStore(NAMESPACE).put(PAGE_KEY, page);
  }

  @Override
  public void afterEach(ExtensionContext context) {
    Page page = context.getStore(NAMESPACE).remove(PAGE_KEY, Page.class);
    if (page != null && !page.isClosed()) {
      page.close();
    }

    // Deciding pass/fail has to happen here rather than via TestWatcher: by the time
    // TestWatcher's callbacks fire, the method-level store may already be closed, and
    // getExecutionException() is exactly the outcome-inspection API meant for use from an
    // AfterEachCallback, unlike TestWatcher which observes after the fact.
    boolean failed = context.getExecutionException().isPresent();
    if (failed) {
      SessionHolder holder = sessionStore(context).remove(SESSION_KEY, SessionHolder.class);
      if (holder != null) {
        holder.failed = true;
        holder.close();
      }
    } else if (!isGroupedMode(context)) {
      SessionHolder holder = sessionStore(context).remove(SESSION_KEY, SessionHolder.class);
      if (holder != null) {
        holder.close();
      }
    }
  }

  @Override
  public void afterAll(ExtensionContext context) {
    // Meaningful in grouped mode only: closes/reports whatever session is still open for this
    // class once every method has run. In per-test mode each session is already closed right
    // after its own test by afterEach above.
    SessionHolder holder = context.getStore(NAMESPACE).remove(SESSION_KEY, SessionHolder.class);
    if (holder != null) {
      holder.close();
    }
  }

  private SessionHolder createHolder(ExtensionContext context) {
    Playwright playwright = Playwright.create();
    SauceSession session =
        SauceSession.create(
            REGION,
            requireEnv("SAUCE_USERNAME"),
            requireEnv("SAUCE_ACCESS_KEY"),
            BROWSER_NAME,
            sessionName(context));
    Browser browser = session.connect(playwright, BROWSER_NAME);
    return new SessionHolder(playwright, session, browser);
  }

  private String sessionName(ExtensionContext context) {
    String className = context.getRequiredTestClass().getSimpleName();
    return isGroupedMode(context)
        ? className
        : className + ": " + context.getRequiredTestMethod().getName();
  }

  private ExtensionContext.Store sessionStore(ExtensionContext context) {
    if (isGroupedMode(context)) {
      return context.getParent().orElseThrow().getStore(NAMESPACE);
    }
    return context.getStore(NAMESPACE);
  }

  private boolean isGroupedMode(ExtensionContext context) {
    return !"concurrent"
        .equalsIgnoreCase(
            context
                .getConfigurationParameter("junit.jupiter.execution.parallel.mode.default")
                .orElse("same_thread"));
  }

  private static String requireEnv(String name) {
    String value = System.getenv(name);
    if (value == null || value.isEmpty()) {
      throw new IllegalStateException("Missing required environment variable: " + name);
    }
    return value;
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext) {
    Class<?> type = parameterContext.getParameter().getType();
    return type == Browser.class || type == Page.class || type == SauceSession.class;
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext) {
    Class<?> type = parameterContext.getParameter().getType();
    if (type == Page.class) {
      return extensionContext.getStore(NAMESPACE).get(PAGE_KEY, Page.class);
    }
    SessionHolder holder = sessionStore(extensionContext).get(SESSION_KEY, SessionHolder.class);
    return type == Browser.class ? holder.browser : holder.session;
  }
}
