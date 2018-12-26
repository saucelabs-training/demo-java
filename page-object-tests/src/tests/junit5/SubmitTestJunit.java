package tests.junit5;

import org.junit.jupiter.api.*;
import pages.*;
import data.*;

public class SubmitTestJunit extends BaseJunit {

    @Tag("dev")
    @Tag("submit-tests")
    @Test
    @DisplayName("calculateItemTotal()")
    public void calculateItemTotal(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItems();
        inventoryPage.proceedToCheckout();
        CartPage cartPage = CartPage.visit(driver);
        float preCheckout = cartPage.itemTotalCalculator();
        cartPage.checkout();
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.successfulShippingInfo(User.shippingInfo());
        SubmitPageTestNG submitPage = new SubmitPageTestNG(driver);
        float itemTotal = submitPage.getItemTotal();
        Assertions.assertTrue(itemTotal == preCheckout);
        Assertions.assertTrue(submitPage.overviewStatus());
        Assertions.assertFalse(submitPage.emptyCart());
        Assertions.assertTrue(submitPage.totalItemsInCart() >= 1);
        Assertions.assertTrue(submitPage.totalItemsInCart() <= 6);
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + "There were " + submitPage.totalItemsInCart() + " total items in the cart\n" + testInfo);
    }

    @Tag("dev")
    @Tag("submit-tests")
    @Test
    @DisplayName("calculateSubTotal()")
    public void calculateSubTotal(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItems();
        inventoryPage.proceedToCheckout();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.checkout();
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.successfulShippingInfo(User.shippingInfo());
        SubmitPageTestNG submitPage = new SubmitPageTestNG(driver);
        float itemTotal = submitPage.getFinalTotal();
        float totalAmount = submitPage.getTaxAmount() + submitPage.getItemTotal();
        Assertions.assertTrue(itemTotal == totalAmount);
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + "There were " + submitPage.totalItemsInCart() + " total items in the cart\n" + testInfo+" \n");
    }
}
