package com.yourcompany.Tests;

import com.yourcompany.Pages.AnotherPage;
import com.yourcompany.Pages.GuineaPigPage;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.rmi.UnexpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class SampleSauceFollowLinkTest extends SampleSauceTestBase {

    /**
     * Runs a simple test verifying the checked checkbox state
     *
     * @throws InvalidElementStateException
     */
    @Test(dataProvider = "hardCodedBrowsers")
    public void verifyUncheckedCheckBoxInputTest(String browser, String version, String os, Method method)
            throws MalformedURLException, InvalidElementStateException, UnexpectedException {

        //create webdriver session
        this.createDriver(browser, version, os, method.getName());
        WebDriver driver = this.getWebDriver();

        // initialize page object
        GuineaPigPage gpage = PageFactory.initElements(driver, GuineaPigPage.class);

        gpage.visitPage();
        gpage.followLink();

        AnotherPage apage = PageFactory.initElements(driver, AnotherPage.class);

        assertEquals(apage.title, apage.getTitle(driver));

    }

}