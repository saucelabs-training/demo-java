package tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;
import data.User;
import pages.CartPage;
import pages.InventoryPage;
import pages.ShippingPage;
import pages.SubmitPage;

import java.lang.reflect.Method;

public class SubmitTestTestNG extends BaseTestNG {

    @Tag(name = "calculateItemTotal()")
    @Test
    /** Tests the full customer Journey and calucluates the total item prices before taxes,
     *     and cross-references it with what was displayed in the cart page before checkout
     */
    public void calculateItemTotal(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItems();
        inventoryPage.proceedToCheckout();
        CartPage cartPage = CartPage.visit(driver);
        float preCheckout = cartPage.itemTotalCalculator();
        cartPage.checkout();
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.successfulShippingInfo(User.shippingInfo());
        SubmitPage submitPage = new SubmitPage(driver);
        float itemTotal = submitPage.getItemTotal();
        Assert.assertTrue(itemTotal == preCheckout);
        Assert.assertTrue(submitPage.overviewStatus());
        Assert.assertFalse(submitPage.emptyCart());
        Assert.assertTrue(submitPage.totalItemsInCart() >= 1);
        Assert.assertTrue(submitPage.totalItemsInCart() <= 6);
        System.out.println("Test: " + method.getName() + " Executed\n" +
                "There were " + submitPage.totalItemsInCart() + " total items in the cart\n");
    }

    @Tag(name = "calcualteSubTotal()")
    @Test
    /** Tests the full customer journey and checks if the tax calculation matches with the item sub total **/
    public void calculateSubTotal(Method method) {
        InventoryPage inventoryPage = InventoryPage.visit(driver);
        inventoryPage.addRandomItems();
        inventoryPage.proceedToCheckout();
        CartPage cartPage = CartPage.visit(driver);
        cartPage.checkout();
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.successfulShippingInfo(User.shippingInfo());
        SubmitPage submitPage = new SubmitPage(driver);
        float itemTotal = submitPage.getFinalTotal();
        float totalAmount = submitPage.getTaxAmount() + submitPage.getItemTotal();
        Assert.assertTrue(itemTotal == totalAmount);
        Assert.assertTrue(submitPage.overviewStatus());
        Assert.assertFalse(submitPage.emptyCart());
        Assert.assertTrue(submitPage.totalItemsInCart() >= 1);
        Assert.assertTrue(submitPage.totalItemsInCart() <= 6);
        System.out.println("Test: " + method.getName() + " Executed\n" + "There were "
                + submitPage.totalItemsInCart() + " total items in the cart\n");
    }
}
