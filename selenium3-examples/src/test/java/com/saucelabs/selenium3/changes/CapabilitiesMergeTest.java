package com.saucelabs.selenium3.changes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
public class CapabilitiesMergeTest {

    @DisplayName("Selenium 3 Can merge in place")
    @Test
    public void mergesInPlace() {
        ChromeOptions options1 = new ChromeOptions();
        ChromeOptions options2 = new ChromeOptions();

        options1.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options2.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);

        options1.merge(options2);

        Assertions.assertEquals(UnexpectedAlertBehaviour.IGNORE, options1.getCapability("unhandledPromptBehavior"));
    }

    @DisplayName("Selenium 3 Can merge as a new object")
    @Test
    public void mergesNewObject() {
        ChromeOptions options1 = new ChromeOptions();
        ChromeOptions options2 = new ChromeOptions();

        options1.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options2.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);

        options1.merge(options2);

        Assertions.assertEquals(UnexpectedAlertBehaviour.IGNORE, options1.getCapability("unhandledPromptBehavior"));
    }
}
