package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SubmitPage extends BasePage {
    private By finishButton = By.cssSelector("a[href='./checkout-complete.html']");
    private By itemSubTotal = By.className("summary_subtotal_label");
    private By itemSummaryTotal = By.className("summary_total_label");
    private By itemTax = By.className("summary_tax_label");

    public static SubmitPageTestNG visit(WebDriver driver) {
        SubmitPageTestNG page = new SubmitPageTestNG(driver);
        driver.get("https://www.saucedemo.com/checkout-step-two.html");
        return page;
    }

    public Float getItemTotal() throws NumberFormatException {
        String number = getInnerText(itemSubTotal).replace("Item total: $", "");
        float result = Float.parseFloat(number);
        System.out.println("SubmitPageTestNG.getItemTotal() = " + result);
        return result;
    }

    public Float getFinalTotal() throws NumberFormatException {
        String number = getInnerText(itemSummaryTotal).replace("Total: $", "");
        float result = Float.parseFloat(number);
        System.out.println("SubmitPageTestNG.getFinalTotal = " + result);
        return result;
    }
    public SubmitPage(WebDriver driver) {
        this.driver = driver;
    }
    public Boolean overviewStatus() { return findTotal(finishButton) > 0;}

    public Float taxCalculator() {
        float beforeTax = getItemTotal();
        float tax = 8;
        float sum;
        tax = beforeTax * (tax/100);
        sum = tax + beforeTax;
        System.out.println("submitPage.taxCalculator() = "  + sum);
        return sum;
    }

    public Float getTaxAmount() throws NumberFormatException {
        String number = getInnerText(itemTax).replace("Tax: $", "");
        float result = Float.parseFloat(number);
        System.out.println("SubmitPageTestNG.getTaxAmount = " + result);
        return result;
    }
}
