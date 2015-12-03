package com.yourcompany.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GuineaPig {

	private static WebElement element = null;
	private static String title = null;
	
	public static WebDriver getGuineaPigHome(WebDriver driver){
		   
		driver.get("https://saucelabs.com/test/guinea-pig");
		return driver;
	}
	
	public static WebElement findLink(WebDriver driver){
	   
		element = driver.findElement(By.id("i am a link"));
		return element;
	}
			
}
