package tests.junit5;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import pages.CartPage;
import pages.InventoryPage;
import pages.ProductPage;

public class InventoryTestJunit extends BaseJunit {

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("oneItemInCart()")
    public void oneItemInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addOneItem();
        Assertions.assertEquals(inventoryPage.itemCount(), "1");
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("twoItemsInCart()")
    public void twoItemsInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addTwoItems();
        Assertions.assertEquals(inventoryPage.itemCount(),  "2");
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("everyItemInCart()")
    public void everyItemInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        Assertions.assertEquals(inventoryPage.itemCount(), "6" );
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("zeroItemsInCart()")
    public void zeroItemsInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        inventoryPage.removeAllItems();
        Assertions.assertTrue(inventoryPage.emptyCart());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("randomItemInCart()")
    public void randomItemInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItem();
        Assertions.assertFalse(inventoryPage.emptyCart());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("randomItemsInCart()")
    public void multipleRandomItemsInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItems();
        Assertions.assertFalse(inventoryPage.emptyCart());
        Assertions.assertTrue(inventoryPage.totalItemsInCart() <= 6);
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("checkoutItemsInCart()")
    public void checkoutItemsInCart(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        inventoryPage.proceedToCheckout();
        CartPage cartPage = new CartPage(driver);
        Assertions.assertTrue(cartPage.checkoutStatus());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);

    }

    @Tag("dev")
    @Tag("inventory-tests")
    @Test
    @DisplayName("checkoutItemsInCart()")
    public void checkRandomItemPage(TestInfo testInfo) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        inventoryPage.clickRandomLink();
        ProductPage productPage = new ProductPage(driver);
        Assertions.assertTrue(productPage.productStatus());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);

    }
}
