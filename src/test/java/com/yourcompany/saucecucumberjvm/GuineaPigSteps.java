package com.yourcompany.saucecucumberjvm;

import Utils.ResultReporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class GuineaPigSteps {

	private ResultReporter resultReporter;

	public static WebDriver driver;

	@Before
	public void setUp(Scenario scenario) throws Throwable {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("testobject_api_key", System.getenv("RDC_API_KEY"));
		caps.setCapability("platformName", System.getenv("platformName"));
		
	    driver = new RemoteWebDriver(new URL("https://us1.appium.testobject.com/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		resultReporter = new ResultReporter();

	}

	@Given("^I am on the Sauce Labs Guinea Pig test page$")
	public void user_is_on_guinea_pig_page() throws Throwable {
        driver.get("https://saucelabs.com/test/guinea-pig");
	}

	@When("^I click on the link$")
	public void user_click_on_the_link() throws Throwable {
		driver.findElement(By.linkText("i am a link")).click();
	}

	@Then("^I should see a new page$")
	public void new_page_displayed() throws Throwable {
		String page_title = driver.getTitle();
		Assert.assertTrue(page_title.equals("I am another page title - Sauce Labs"));
	}

	@After
	public void tearDown(Scenario scenario) throws Throwable {

		boolean success = !scenario.isFailed();

		String sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
		resultReporter.saveTestStatus(sessionId, success);
		driver.quit();

	}
}