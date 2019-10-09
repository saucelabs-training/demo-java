package realdevice_example.android.Tests;

import emusim_example.android.Pages.GuineaPigPage;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

/**
 * Created by mehmetgerceker on 12/7/15.
 * Updated by spider@saucelabs.com on 10/8/19.
 */

public class FollowLinkTest extends TestBase {

    /**
     * Runs a simple test verifying link can be followed.
     *
     * @throws InvalidElementStateException
     */
    @Test(dataProvider = "hardCodedBrowsers")
    public void verifyLinkTest(String platformVersion,
                               String deviceName,
                               String deviceOrientation,
                               Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {

        //create webdriver session
        this.createDriver(platformVersion, deviceName, deviceOrientation, method.getName());
        WebDriver driver = this.getAndroidDriver();

        GuineaPigPage page = new GuineaPigPage(driver);

        page.followLink();

        Assert.assertFalse(page.isOnPage());
    }

}