package com.yourcompany.saucecucumberjvm;

import cucumber.api.Scenario;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;
import java.net.URL;

import Utils.SauceUtils;

public class GuineaPigSteps {

	public static final String USERNAME = System.getenv("SAUCE_USERNAME");
	public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
	public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	public static WebDriver driver;
	public String sessionId;
	public String jobName;

	@Before
	public void setUp(Scenario scenario) throws Throwable {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", System.getenv("platform"));
        caps.setCapability("browserName", System.getenv("browserName"));
        caps.setCapability("version", System.getenv("version"));
        caps.setCapability("name", scenario.getName());

	    driver = new RemoteWebDriver(new URL(URL), caps);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        jobName = caps.getCapability("name").toString();

        sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
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
		driver.quit();
		SauceUtils.UpdateResults(USERNAME, ACCESS_KEY, !scenario.isFailed(), sessionId);
		System.out.println("SauceOnDemandSessionID="+ sessionId + "job-name="+ jobName);
	}
}