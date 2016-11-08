package com.yourcompany.Tests;


import com.yourcompany.Pages.*;
import com.yourcompany.Tests.SampleSauceTestBase;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import static org.hamcrest.CoreMatchers.containsString;
import org.openqa.selenium.support.PageFactory;

import java.util.UUID;

import static org.junit.Assert.*;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class SampleSauceTextInputTest extends SampleSauceTestBase {

    public SampleSauceTextInputTest(String os,
                                    String version, String browser, String deviceName, String deviceOrientation) {
            super(os, version, browser, deviceName, deviceOrientation);
    }

    /**
     * Runs a simple test verifying if the comment input is functional.
     * @throws InvalidElementStateException
     */
    @Test
    public void verifyCommentInputTest() throws InvalidElementStateException {
        String commentInputText = UUID.randomUUID().toString();

        GuineaPigPage page = PageFactory.initElements(driver, GuineaPigPage.class);
        page.visitPage();
        page.submitComment(commentInputText);

        assertThat(page.getSubmittedCommentText(), containsString(commentInputText));

    }
}