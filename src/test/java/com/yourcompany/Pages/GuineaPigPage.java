package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class GuineaPigPage extends PageBase {

    @FindBy(linkText = "i am a link")
    private WebElement theActiveLink;

    @FindBy(id = "your_comments")
    private WebElement yourCommentsSpan;

    @FindBy(id = "comments")
    private WebElement commentsTextAreaInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public GuineaPigPage(WebDriver driver) {
        this.driver = driver;
    }

    public void visitPage() {
        driver.get("https://saucelabs.com/test/guinea-pig");
    }

    public void followLink() {
        clickLink(this.theActiveLink);
    }

    public void submitComment(String text) {
        setTextAreaInputValue(this.commentsTextAreaInput, text);
        clickButton(this.submitButton);

        // Race condition for time to populate yourCommentsSpan
        WebDriverWait wait = new WebDriverWait(this.driver, 15);
        wait.until(ExpectedConditions.textToBePresentInElement(yourCommentsSpan, text));
    }

    public String getSubmittedCommentText() {
        return this.yourCommentsSpan.getText();
    }
}
