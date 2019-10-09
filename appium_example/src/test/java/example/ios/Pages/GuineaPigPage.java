package example.ios.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;

public class GuineaPigPage {

    @FindBy(id = "h1Text")
    private WebElement h1Text;

    @FindBy(id = "i am a link")
    private WebElement theActiveLink;

    @FindBy(id = "submittedComments")
    private WebElement yourComments;

    @FindBy(id = "comments")
    private WebElement commentsTextInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    public WebDriver driver;

    public GuineaPigPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void submitComment(String text) {
        this.commentsTextInput.click();
        this.commentsTextInput.sendKeys(text);
        hideKeyboard();
        this.submitButton.click();
    }

    public String getSubmittedCommentText() {
        return this.yourComments.getText();
    }

    public void followLink() {
        this.theActiveLink.click();
    }

    public boolean isOnPage() {
        try {
            getSubmittedCommentText();
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    /**
     * This method only work for this page and assumes the app supports keyboard hide on click-away.
     * In appium there's no way of doing this with a generalized method for iOS as of yet.
     */
    public void hideKeyboard() {
        this.h1Text.click();
    }
}
