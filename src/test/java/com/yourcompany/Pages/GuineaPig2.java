package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;

public class GuineaPig2 {

	private static String title = null;
	
	
	public static WebDriver getGuineaPigPage2(WebDriver driver){
		   
		driver.get("https://saucelabs.com/test-guinea-pig2.html");
		return driver;
	}
	
}

