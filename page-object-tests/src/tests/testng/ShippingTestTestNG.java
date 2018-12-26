package tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;
import data.User;
import pages.ShippingPage;
import pages.SubmitPage;

import java.lang.reflect.Method;

public class ShippingTestTestNG extends BaseTestNG {

    @Tag(name = "enterShippingAddress()")
    @Test
    /** Tests the shipping information page process **/
    public void enterShippingAddress(Method method) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.successfulShippingInfo(User.shippingInfo());
        SubmitPage submitPage = new SubmitPage(driver);
        Assert.assertTrue(submitPage.overviewStatus());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "enterBlankLastName()")
    @Test
    /** Tests the shipping information page process for a blank last name error **/
    public void enterBlankLastName(Method method) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.unsuccessfulShippingInfo(User.blankLastName());
        Assert.assertTrue(shippingPage.hasErrorMessage());
        Assert.assertTrue(shippingPage.nullLastName());
        System.out.println("Test: " + method.getName() + " Executed\n" );
    }

    @Tag(name = "enterBlankFirstName()")
    @Test
    /** Tests the shipping information page process for a blank first name error **/
    public void enterBlankFirstName(Method method) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.unsuccessfulShippingInfo(User.blankFirstName());
        Assert.assertTrue(shippingPage.hasErrorMessage());
        Assert.assertTrue(shippingPage.nullFirstName());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "enterBlankZipCode()")
    @Test
    /** Tests the shipping information page process for a blank zip code error **/
    public void enterBlankZipCode(Method method) {
        ShippingPage shippingPage = ShippingPage.visit(driver);
        shippingPage.unsuccessfulShippingInfo(User.blankZipCode());
        Assert.assertTrue(shippingPage.hasErrorMessage());
        Assert.assertTrue(shippingPage.nullZipCode());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }
}