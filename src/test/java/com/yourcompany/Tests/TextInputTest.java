package com.yourcompany.Tests;

import com.yourcompany.Pages.GuineaPigPage;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.UUID;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class TextInputTest extends TestBase {

    /**
     * Runs a simple test verifying if the comment input is functional.
     * @throws InvalidElementStateException
     */
    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void verifyCommentInputTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {
        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        String commentInputText = UUID.randomUUID().toString();

        this.annotate("Visiting GuineaPig page...");
        GuineaPigPage page = GuineaPigPage.visitPage(driver);

        this.annotate(String.format("Submitting comment: \"%s\"", commentInputText));
        page.submitComment(commentInputText);

        this.annotate(String.format("Asserting submitted comment is: \"%s\"", commentInputText));
        Assert.assertTrue(page.getSubmittedCommentText().contains(commentInputText));
    }

}