import org.testng.Assert;
import org.testng.annotations.Test;


public class CheckoutFeatureTest extends BaseTest {

    @Test
    public void ShouldBeAbleToCheckoutWithItems() {
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        confirmationPage.visit();
        Assert.assertTrue(confirmationPage.isLoaded());

        confirmationPage.setPageState();
        Assert.assertTrue(confirmationPage.hasItems());
        CheckoutCompletePage completePage = confirmationPage.finishCheckout();
        Assert.assertTrue(completePage.isLoaded());
    }

}
