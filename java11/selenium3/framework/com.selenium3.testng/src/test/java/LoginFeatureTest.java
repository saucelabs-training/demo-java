import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginFeatureTest extends BaseTest {

    @Test
    public void ShouldBeAbleToLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        Assert.assertTrue(loginPage.isLoaded());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(inventoryPage.isLoaded());
    }
}
