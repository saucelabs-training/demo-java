package com.yourcompany.Tests;


import com.yourcompany.Pages.*;
import com.yourcompany.Tests.SampleSauceTestBase;
import org.junit.Test;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.UUID;

import static org.junit.Assert.*;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class SampleSauceTextInputTest extends SampleSauceTestBase {

    /**
     * Runs a simple test verifying if the email input is functional.
     * @throws InvalidElementStateException
     */
    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void verifyEmailInputTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {
        String emailInputText = "abc@gmail.com";
        WebDriver driver = createDriver(browser, version, os, method.getName());
        // Navigate to the page
        driver.get("https://saucelabs.com/test/guinea-pig");

        // get page object
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         enterEmailText page is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.enterEmailText(emailInputText);

        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(page.getEmailText(), emailInputText);

    }

    /**
     * Runs a simple test verifying if the comment input is functional.
     * @throws InvalidElementStateException
     */
    @org.testng.annotations.Test(dataProvider = "hardCodedBrowsers")
    public void verifyCommentInputTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {
        String commentInputText = UUID.randomUUID().toString();

        WebDriver driver = createDriver(browser, version, os, method.getName());

        driver.get("https://saucelabs.com/test/guinea-pig");

        // Navigate to the page
        GuineaPigPage page = GuineaPigPage.getPage(driver);

        /*
         enterCommentText page is an exposed "service",
             which interacts with the email input field element by sending text to it.
        */
        page.enterCommentText(commentInputText);

        /*
         Assertions should be part of test and not part of Page object.
         Each test should be verifying one piece of functionality (atomic testing)
        */
        assertEquals(commentInputText, page.getCommentText());

    }
}