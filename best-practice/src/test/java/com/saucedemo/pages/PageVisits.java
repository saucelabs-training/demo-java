package com.saucedemo.pages;

import org.openqa.selenium.remote.RemoteWebDriver;

public interface PageVisits {
    default void visit() {
        getDriver().get("https://www.saucedemo.com/" + getPagePart());
    }

    RemoteWebDriver getDriver();

    String getPagePart();
}
