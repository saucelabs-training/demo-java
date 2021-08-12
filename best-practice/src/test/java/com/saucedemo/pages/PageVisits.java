package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public interface PageVisits {
    /**
     * Unless overridden this is the right way to navigate to the provided page
     */
    default void visit() {
        getDriver().get("https://www.saucedemo.com/" + getPagePart());
    }

    RemoteWebDriver getDriver();

    String getPagePart();
}
