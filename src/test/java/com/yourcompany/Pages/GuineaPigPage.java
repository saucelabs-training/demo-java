package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GuineaPigPage {

    @FindBy(linkText = "i am a link")
    private WebElement theActiveLink;

    @FindBy(id = "your_comments")
    private WebElement yourCommentsSpan;

    @FindBy(id = "comments")
    private WebElement commentsTextAreaInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public WebDriver driver;
    public static String url = "https://saucelabs-sample-test-frameworks.github.io/training-test-page";

    public static GuineaPigPage visitPage(WebDriver driver) {
        GuineaPigPage page = new GuineaPigPage(driver);
        page.visitPage();
        return page;
    }

    public GuineaPigPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void visitPage() {
        this.driver.get(url);
    }

    public void followLink() {
        this.theActiveLink.click();
    }

    public void submitComment(String text) {
        this.commentsTextAreaInput.sendKeys(text);
        this.submitButton.click();

        // Race condition for time to populate yourCommentsSpan
        WebDriverWait wait = new WebDriverWait(this.driver, 15);
        wait.until(ExpectedConditions.textToBePresentInElement(yourCommentsSpan, text));
    }

    public String getSubmittedCommentText() {
        return this.yourCommentsSpan.getText();
    }

    public boolean isOnPage() {
        String title = "I am a page title - Sauce Labs";
        return this.driver.getTitle() == title;
    }

}
