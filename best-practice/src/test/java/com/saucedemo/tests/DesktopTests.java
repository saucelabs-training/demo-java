package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.junit4.SauceBaseTest;
import com.saucelabs.saucebindings.options.SauceOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.TimeoutException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** Desktop Tests. */
@RunWith(Parameterized.class)
public class DesktopTests extends SauceBaseTest {
    /*
     * Configure our data driven parameters
     * */
    @Parameterized.Parameter
    public Browser browserName;
    @Parameterized.Parameter(1)
    public String browserVersion;
    @Parameterized.Parameter(2)
    public SaucePlatform platform;

    @Parameterized.Parameters()
    public static Collection<Object[]> crossBrowserData() {
        return Arrays.asList(new Object[][] {
                { Browser.CHROME, "latest", SaucePlatform.WINDOWS_10 },
                // { Browser.CHROME, "latest-1", SaucePlatform.WINDOWS_10 }
                /*{ Browser.SAFARI, "latest", SaucePlatform.MAC_MOJAVE },
                { Browser.CHROME, "latest", SaucePlatform.MAC_MOJAVE }
                
                         // Duplication below for demo purposes of massive parallelization
                         {Browser.CHROME, "latest", SaucePlatform.WINDOWS_10},
                         {Browser.CHROME, "latest-1", SaucePlatform.WINDOWS_10},
                         {Browser.SAFARI, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.WINDOWS_10},
                         {Browser.CHROME, "latest-1", SaucePlatform.WINDOWS_10},
                         {Browser.SAFARI, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.WINDOWS_10},
                         {Browser.CHROME, "latest-1", SaucePlatform.WINDOWS_10},
                         {Browser.SAFARI, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.WINDOWS_10},
                         {Browser.CHROME, "latest-1", SaucePlatform.WINDOWS_10},
                         {Browser.SAFARI, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.WINDOWS_10},
                         {Browser.CHROME, "latest-1", SaucePlatform.WINDOWS_10},
                         {Browser.SAFARI, "latest", SaucePlatform.MAC_MOJAVE},
                         {Browser.CHROME, "latest", SaucePlatform.MAC_MOJAVE},
                */
        });
    }

    @Override
    public SauceOptions createSauceOptions() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setBrowserName(browserName);
        sauceOptions.setBrowserVersion(browserVersion);
        sauceOptions.setPlatformName(platform);

        return sauceOptions;
    }

    @Test()
    public void loginWorks() {
        int numLoops = getEnvNumber("LOGIN_LOOPS");
        System.out.println(numLoops);
        for(int i=0;i<numLoops;i++){
            LoginPage loginPage = new LoginPage(driver);
            loginPage.visit();
            loginPage.login("standard_user");
            assertTrue(new ProductsPage(driver).isDisplayed());            
            if(getEnv("PRIVATE_API_CALL").equals("TRUE")){
                callAPI();
            }
        }
    }

    @Test(expected = TimeoutException.class)
    public void lockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("locked_out_user");
        assertFalse(new ProductsPage(driver).isDisplayed());
    }

    @Test(expected = TimeoutException.class)
    public void invalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.visit();
        loginPage.login("foo_bar_user");
        assertFalse(new ProductsPage(driver).isDisplayed());
    }

    private void callAPI() {
        HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:8000/v1/pets"))
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
        HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if(response != null){
		    System.out.println(response.body());
        }
    }

    private String getEnv(String name){
        if(System.getProperty(name) == null) {
            return "";
        }
        return System.getProperty(name);
    }

    private int getEnvNumber(String name){
        if(System.getenv(name) == null) {
            return 0;
        }
        return Integer.valueOf(System.getenv(name));
    }
    
}
