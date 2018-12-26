package tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;
import pages.CartPage;
import pages.InventoryPage;
import pages.ProductPage;

import java.lang.reflect.Method;

public class InventoryTestTestNG extends BaseTestNG {

    @Tag(name = "oneItemInCart()")
    @Test
    /** Tests placing one specific item in the cart **/
    public void oneItemInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addOneItem();
        Assert.assertEquals(inventoryPage.itemCount(), "1");
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "twoItemsInCart()")
    @Test
    /** Tests placing two specific items in the cart **/
    public void twoItemsInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addTwoItems();
        Assert.assertEquals(inventoryPage.itemCount(),  "2");
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "everyItemInCart()")
    @Test
    /** Tests placing all the available items on the page in the cart **/
    public void everyItemInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        Assert.assertEquals(inventoryPage.itemCount(), "6" );
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "zeroItemsInCart()")
    @Test
    /** Tests placing all items in the cart, then removing them **/
    public void zeroItemsInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        inventoryPage.removeAllItems();
        Assert.assertTrue(inventoryPage.emptyCart());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "randomItemInCart()")
    @Test
    /** Tests placing one random item in the cart **/
    public void randomItemInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItem();
        Assert.assertFalse(inventoryPage.emptyCart());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "randomItemsInCart()")
    @Test
    /** Tests placing multiple random items in the cart **/
    public void multipleRandomItemsInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItems();
        Assert.assertFalse(inventoryPage.emptyCart());
        Assert.assertTrue(inventoryPage.totalItemsInCart() <= 6);
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "checkoutItemsInCart")
    @Test
    /** Tests the checkout prices with all items in the cart **/
    public void checkoutItemsInCart(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        inventoryPage.proceedToCheckout();
        CartPage cartPage = new CartPage(driver);
        Assert.assertTrue(cartPage.checkoutStatus());
        System.out.println("Test: " + method.getName() + " Executed\n");

    }

    @Tag(name = "checkRandomItemPage()")
    @Test
    /** Tests a random product link to see if it navigates to the product page **/
    public void checkRandomItemPage(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        inventoryPage.clickRandomLink();
        ProductPage productPage = new ProductPage(driver);
        Assert.assertTrue(productPage.productStatus());
        System.out.println("Test: " + method.getName() + " Executed\n");

    }
}
