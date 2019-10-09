package example.android.Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GuineaPigPage {

    @FindBy(id = "Heading1_1")
    private WebElement h1Text;

    @FindBy(id = "i_am_a_link")
    private WebElement theActiveLink;

    @FindBy(id = "your_comments")
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
     */
    public void hideKeyboard() {
        this.h1Text.click();
    }
}
