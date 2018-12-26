package tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;
import pages.CartPage;
import pages.InventoryPage;
import pages.ShippingPage;

import java.lang.reflect.Method;

public class CartTestTestNG extends BaseTestNG {

    @Tag(name = "removeRandomItem()")
    @Test
    /** Tests removing a random item from the cart **/
    public void removeRandomItem(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.removeRandom();
        Assert.assertEquals(cartPage.itemCount(), "5");
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "cancelCheckout()")
    @Test
    /** Tests canceling checkout process with items still in cart **/
    public void cancelCheckout(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.cancel();
        Assert.assertEquals(inventoryPage.itemCount(), "6");
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "confirmCheckout()")
    @Test
    /** Tests checkout process **/
    public void confirmCheckout(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addAllItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.checkout();
        ShippingPage shippingPage = ShippingPage.visit(driver);
        Assert.assertTrue(shippingPage.readyToShip());
        Assert.assertEquals(shippingPage.itemCount(), "6");
        System.out.println("Test: " + method.getName() + " Executed\n");
    }


    @Tag(name = "countItemTypes()")
    @Test
    /** Tests the item count during checkout process **/
    public void countItemTypes(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addTwoItems();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.totalItemTypes();
        Assert.assertTrue(cartPage.totalItemTypes() == 2);
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

   /*
    @Tag(name ="countItemQuantity()")
    @Test
    public void countItemQuantity(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addTwoBackPacks();
        inventoryPage.addTwoJackets();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.totalItemTypes();
        Assert.assertEquals(cartPage.getItemQuantity(), "4");
        System.out.println("Test: " + method.getName() + " Executed\n");
    }*/


}
