package tests.junit5;

import org.junit.jupiter.api.*;
import pages.SubmitPageTestNG;
import data.*;
import pages.ShippingPage;

public class ShippingTestJunit extends BaseJunit {

    @Tag("dev")
    @Tag("shipping-tests")
    @Test
    @DisplayName("enterShippingAddress()")
    public void enterShippingAddress(TestInfo testInfo) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.successfulShippingInfo(User.shippingInfo());
        SubmitPageTestNG submitPage = new SubmitPageTestNG(driver);
        Assertions.assertTrue(submitPage.overviewStatus());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("shipping-tests")
    @Test
    @DisplayName("enterBlankLastName()")
    public void enterblankLastName(TestInfo testInfo) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.unsuccessfulShippingInfo(User.blankLastName());
        Assertions.assertTrue(shippingPage.hasErrorMessage());
        Assertions.assertTrue(shippingPage.nullLastName());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("shipping-tests")
    @Test
    @DisplayName("enterBlankFirstName()")
    public void enterBlankFirstName(TestInfo testInfo) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.unsuccessfulShippingInfo(User.blankFirstName());
        Assertions.assertTrue(shippingPage.hasErrorMessage());
        Assertions.assertTrue(shippingPage.nullFirstName());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("shipping-tests")
    @Test
    @DisplayName("enterBlankZipCode()")
    public void enterBlankZipCode(TestInfo testInfo) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.unsuccessfulShippingInfo(User.blankZipCode());
        Assertions.assertTrue(shippingPage.hasErrorMessage());
        Assertions.assertTrue(shippingPage.nullZipCode());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }
}