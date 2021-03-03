package com.saucedemo;

import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.ConfirmationPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CheckoutFeatureTest extends BaseTest {

    @Test
    public void shouldBeAbleToCheckoutWithItems() {
        ConfirmationPage confirmationPage = new ConfirmationPage(getDriver());
        confirmationPage.visit();
        Assert.assertTrue(confirmationPage.isLoaded());

        confirmationPage.setPageState();
        Assert.assertTrue(confirmationPage.hasItems());
        CheckoutCompletePage completePage = confirmationPage.finishCheckout();
        Assert.assertTrue(completePage.isLoaded());
    }
    @Test
    public void shouldBeAbleToCheckoutWithItems2() {
        ConfirmationPage confirmationPage = new ConfirmationPage(getDriver());
        confirmationPage.visit();
        Assert.assertTrue(confirmationPage.isLoaded());

        confirmationPage.setPageState();
        Assert.assertTrue(confirmationPage.hasItems());
        CheckoutCompletePage completePage = confirmationPage.finishCheckout();
        Assert.assertTrue(completePage.isLoaded());
    }
    @Test
    public void shouldBeAbleToCheckoutWithItems3() {
        ConfirmationPage confirmationPage = new ConfirmationPage(getDriver());
        confirmationPage.visit();
        Assert.assertTrue(confirmationPage.isLoaded());

        confirmationPage.setPageState();
        Assert.assertTrue(confirmationPage.hasItems());
        CheckoutCompletePage completePage = confirmationPage.finishCheckout();
        Assert.assertTrue(completePage.isLoaded());
    }
    @Test
    public void shouldBeAbleToCheckoutWithItems4() {
        ConfirmationPage confirmationPage = new ConfirmationPage(getDriver());
        confirmationPage.visit();
        Assert.assertTrue(confirmationPage.isLoaded());

        confirmationPage.setPageState();
        Assert.assertTrue(confirmationPage.hasItems());
        CheckoutCompletePage completePage = confirmationPage.finishCheckout();
        Assert.assertTrue(completePage.isLoaded());
    }
    @Test
    public void shouldBeAbleToCheckoutWithItems5() {
        ConfirmationPage confirmationPage = new ConfirmationPage(getDriver());
        confirmationPage.visit();
        Assert.assertTrue(confirmationPage.isLoaded());

        confirmationPage.setPageState();
        Assert.assertTrue(confirmationPage.hasItems());
        CheckoutCompletePage completePage = confirmationPage.finishCheckout();
        Assert.assertTrue(completePage.isLoaded());
    }
}
