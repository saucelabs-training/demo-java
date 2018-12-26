package tests.junit5;

import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import pages.LogInPage;
import pages.InventoryPage;
import data.*;


public class LogInTestJunit extends BaseJunit {

    @Tag("dev")
    @Tag("auth-tests")
    @Test
    @DisplayName("logInSuccessfully()")
    public void logInSuccessfully(TestInfo testInfo) {
        LogInPage logInPage = LogInPage.visit(driver);
        logInPage.signIn(User.validUser());
        InventoryPage inventoryPage = new InventoryPage(driver);
        Assertions.assertTrue(inventoryPage.isSignedIn());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("auth-tests")
    /** @Disabled **/
    @Test
    @DisplayName("logInUnsuccessfully()")
    public void logInUnsuccessfully(TestInfo testInfo) {
        LogInPage logInPage = LogInPage.visit(driver);
        logInPage.signInUnsuccessfully(User.invalidUser());
        Assertions.assertTrue(logInPage.hasErrorMessage());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }

    @Tag("dev")
    @Tag("auth-tests")
    @Test
    @DisplayName("logInBlankPassword()")
    public void logInBlankPassword(TestInfo testInfo) {
        LogInPage logInPage = LogInPage.visit(driver);
        logInPage.signInUnsuccessfully(User.blankPassword());
        Assertions.assertTrue(logInPage.hasErrorMessage());
        System.out.println("Test: " + testInfo.getDisplayName() + " Executed\n" + testInfo);
    }
}
