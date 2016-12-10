package com.yourcompany.Tests;

import com.yourcompany.Pages.*;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class FollowLinkTest extends TestBase {

    public FollowLinkTest(String os,
                          String version, String browser, String deviceName, String deviceOrientation) {
            super(os, version, browser, deviceName, deviceOrientation);
    }

    /**
     * Runs a simple test verifying link can be followed.
     * @throws InvalidElementStateException
     */
    @Test
    public void verifyLinkTest() throws InvalidElementStateException {
        GuineaPigPage page = PageFactory.initElements(driver, GuineaPigPage.class);

        page.visitPage();
        page.followLink();

        assertFalse(page.isOnPage(driver));
    }
}