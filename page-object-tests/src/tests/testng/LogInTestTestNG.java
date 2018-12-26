package tests.testng;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;
import data.User;
import pages.InventoryPage;
import pages.LogInPage;

import java.lang.reflect.Method;


public class LogInTestTestNG extends BaseTestNG {

    @Tag(name = "logInSuccessfully()")
    @Test
    /** Tests for a successful login **/
    public void logInSuccessfully(Method method) {
        LogInPage logInPage = LogInPage.visit(driver);
        logInPage.signIn(User.validUser());
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isSignedIn());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "logInUnsuccessfully")
    @Test
    /** Tests for an unsuccessful login via invalid user **/
    public void logInUnsuccessfully(Method method) {
        LogInPage logInPage = LogInPage.visit(driver);
        logInPage.signInUnsuccessfully(User.invalidUser());
        Assert.assertTrue(logInPage.hasErrorMessage());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }

    @Tag(name = "logInBlankPassword()")
    @Test
    /** Tests for an unsuccessful login via blank password **/
    public void logInBlankPassword(Method method) {
        LogInPage logInPage = LogInPage.visit(driver);
        logInPage.signInUnsuccessfully(User.blankPassword());
        Assert.assertTrue(logInPage.hasErrorMessage());
        System.out.println("Test: " + method.getName() + " Executed\n");
    }
}
