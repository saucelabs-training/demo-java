package com.yourcompany.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GuineaPigPage {
	private final WebDriver driver;
	
	/*
	 Locators should be at the beginning of the file
	*/
	By emailInputFieldLocator = By.id("fbemail");
	
	public GuineaPigPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/*
	 Exposed "Service" for filling out email input field.
	 * @param inputText represents text to be sent to email input field.
	*/
	public void fillOutEmailInput(String inputText) {
		driver.findElement(emailInputFieldLocator).sendKeys(inputText);
	}

	/*
	 * Exposed "Service" for retrieving email input field text content.
	 * @return text of email input field.
	*/
	public String getEmailInput() {
		return driver.findElement(emailInputFieldLocator).getAttribute("value");
	}

}