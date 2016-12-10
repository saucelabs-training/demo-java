package com.yourcompany.Tests;


import com.yourcompany.Pages.AnotherPage;
import com.yourcompany.Pages.GuineaPigPage;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.support.PageFactory;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


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
        GuineaPigPage gpage = PageFactory.initElements(driver, GuineaPigPage.class);
        gpage.visitPage();
        gpage.followLink();

        AnotherPage apage = PageFactory.initElements(driver, AnotherPage.class);

        assertEquals(apage.title, apage.getTitle(driver));

    }
}