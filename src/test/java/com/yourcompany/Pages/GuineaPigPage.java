package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by mehmetgerceker on 12/7/15.
 */

public class GuineaPigPage extends PageBase {

    @FindBy(id="i am a link")
    private WebElement theActiveLink;

    @FindBy(id="your_comments")
    private WebElement yourCommentsSpan;

    @FindBy(id="comments")
    private WebElement commentsTextAreaInput;

    @FindBy(id="submit")
    private WebElement submitButton;

    public static GuineaPigPage getPage(WebDriver driver) {
        driver.get("https://saucelabs.com/test/guinea-pig");
        return PageFactory.initElements(driver, GuineaPigPage.class);
    }

    public void submitComment(String text){
        setTextAreaInputValue(this.commentsTextAreaInput, text);
        clickButton(this.submitButton);

        // Race condition for time to populate yourCommentsSpan
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public String getSubmittedCommentText() {
        return this.yourCommentsSpan.getText();
    }
}
