import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SauceOptions;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.SauceSession;
import org.junit.Test;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;

public class ExtraSauceTest {

    @Test
    public void extraSauce() {
        // https://wiki.saucelabs.com/display/DOCS/Test+Configuration+Options
        SauceOptions sauceOptions = new SauceOptions();

        // Primary Options
        sauceOptions.setBrowserName(Browser.FIREFOX);
        sauceOptions.setBrowserVersion("73.0");
        sauceOptions.setPlatformName(SaucePlatform.WINDOWS_8);

        // Test Details
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Tag 1");
        tags.add("Tag 2");

        sauceOptions.setName("Placeholder Test Name (always set this dynamically)");
        sauceOptions.setBuild("Placeholder Build Name + Number (always set this dynamically)");
        sauceOptions.setTags(tags);

        // System Preferences
        sauceOptions.setScreenResolution("1280x1024");
        sauceOptions.setTimeZone("Alaska");

        // Special Features
        sauceOptions.setExtendedDebugging(true);
        sauceOptions.setCapturePerformance(true);

        // Artifact Toggles
        sauceOptions.setRecordVideo(false);
        sauceOptions.setVideoUploadOnPass(false);
        sauceOptions.setRecordScreenshots(false);
        sauceOptions.setRecordLogs(false);

        // Timeout Values
        sauceOptions.setMaxDuration(1800);
        sauceOptions.setCommandTimeout(300);
        sauceOptions.setIdleTimeout(90);


        SauceSession session = new SauceSession(sauceOptions);
        RemoteWebDriver driver = session.start();
        driver.get("https://www.saucedemo.com/");
        session.stop(true);
    }
}
