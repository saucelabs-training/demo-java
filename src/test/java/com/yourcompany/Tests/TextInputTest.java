package com.yourcompany.Tests;

import com.yourcompany.Pages.*;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.UUID;

import static org.junit.Assert.*;

public class TextInputTest extends TestBase {

    public TextInputTest(String os,
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

        GuineaPigPage page = GuineaPigPage.visitPage(driver);
        page.visitPage();
        page.submitComment(commentInputText);

        assertThat(page.getSubmittedCommentText(), containsString(commentInputText));
    }
}