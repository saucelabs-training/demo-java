package tests.junit5;

import org.junit.jupiter.api.*;
import pages.CartPage;
import pages.InventoryPage;
import pages.ShippingPage;

public class CartTestJunit extends BaseJunit {

    @Tag("dev")
    @Tag("cart-tests")
    @Test
    @DisplayName("removeRandomItem()")
    public void removeRandomItem(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.removeRandom();
        Assertions.assertEquals(cartPage.itemCount(), "5");
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("cart-tests")
    @Test
    @DisplayName("cancelCheckout()")
    public void cancelCheckout(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.cancel();
        Assertions.assertEquals(inventoryPage.itemCount(), "6");
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("cart-tests")
    @Test
    @DisplayName("confirmCheckout()")
    public void confirmCheckout(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.checkout();
        ShippingPage shippingPage = ShippingPage.visit(driver);
        Assertions.assertTrue(shippingPage.readyToShip());
        Assertions.assertEquals(shippingPage.itemCount(), "6");
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("cart-tests")
    @Test
    @DisplayName("countItemTypes()")
    public void countItemTypes(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addTwoItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.totalItemTypes();
        Assertions.assertTrue(cartPage.totalItemTypes() == 2);
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

   /* @Tag("dev")
    @Tag("cart-tests")
    @Test
    @Disabled
    @DisplayName("countItemQuantity()")
    public void countItemQuantity(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addTwoBackPacks();
        inventoryPage.addTwoJackets();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.totalItemTypes();
        Assertions.assertEquals(cartPage.getItemQuantity(), "4");
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }*/


}
