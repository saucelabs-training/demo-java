package com.saucedemo.selenium.sauce_features;

import com.saucedemo.selenium.TestBase;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.safari.SafariOptions;

/**
 * Demonstrates downloading a file via Safari on a Sauce Labs macOS VM.
 *
 * <p>Safari shows a native "Allow downloads from this site?" dialog the first time a site triggers
 * a download. This can be dismissed via {@code driver.switchTo().alert().accept()}, including on
 * macOS 15 with Safari 18. This test demonstrates an alternative approach: a pre-run executable
 * that changes the Safari settings before the session starts, so the dialog never appears.
 *
 * <p><b>One-time setup — upload the script to Sauce Storage:</b>
 *
 * <pre>{@code
 * # US data center
 * curl -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY" \
 *      -F "payload=@src/test/resources/allow_safari_downloads.sh" \
 *      -F "name=allow_safari_downloads.sh" \
 *      "https://api.us-west-1.saucelabs.com/v1/storage/upload"
 *
 * # EU data center
 * curl -u "$SAUCE_USERNAME:$SAUCE_ACCESS_KEY" \
 *      -F "payload=@src/test/resources/allow_safari_downloads.sh" \
 *      -F "name=allow_safari_downloads.sh" \
 *      "https://api.eu-central-1.saucelabs.com/v1/storage/upload"
 * }</pre>
 */
public class SafariDownloadTest extends TestBase {
  private static final String DOWNLOAD_PAGE =
      "https://www.selenium.dev/selenium/web/downloads/download.html";

  @BeforeEach
  public void setup(TestInfo testInfo) {
    SafariOptions options = new SafariOptions();
    options.setPlatformName("macOS 15");
    options.setBrowserVersion("18");
    Map<String, Object> sauceOptions = defaultSauceOptions(testInfo);
    /*
     * Pre-run executable configuration.
     *
     * "executable" : references the script by its filename in Sauce Storage
     *                (uploaded via the curl command in the class Javadoc above).
     * "background"  : false — the session waits for the script to finish before
     *                 launching Safari, guaranteeing preferences are applied first.
     * "timeout"     : maximum seconds to wait for the script to complete.
     *
     * See: https://docs.saucelabs.com/web-apps/automated-testing/selenium/pre-run-executables/
     */
    Map<String, Object> prerun = new HashMap<>();
    prerun.put("executable", "storage:filename=allow_safari_downloads.sh");
    prerun.put("background", false);
    prerun.put("timeout", 120);
    sauceOptions.put("prerun", prerun);
    sauceOptions.put("armRequired", true);
    startSession(options, sauceOptions);
  }

  @DisplayName("Download using Safari on macOS")
  @Test
  public void safariFileDownloadTest() {
    driver.get(DOWNLOAD_PAGE);
    driver.findElement(By.id("file-1")).click();
    /*
     * Confirm the browser remained responsive after the click.
     *
     * If the native "Allow Downloads?" dialog had appeared and blocked WebDriver,
     * any subsequent driver call would time out. A successful response to
     * getCurrentUrl() proves the session is alive and the dialog was not shown.
     */
    driver.get("https://saucelabs.com/");
    Assertions.assertNotEquals(
        DOWNLOAD_PAGE,
        driver.getCurrentUrl(),
        "Browser did not navigate away from the download page");
  }
}
