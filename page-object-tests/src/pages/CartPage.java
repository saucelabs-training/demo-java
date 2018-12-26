package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {
    private By checkoutBtn = By.className("cart_checkout_link");
    private By cartQuantity = By.className("cart_quantity");
    private By continueShopping = By.className("cart_cancel_link");
    private By cartItem = By.className("cart_item");
    private By inventoryPrice = By.className("inventory_item_price");

    public static CartPage visit(WebDriver driver) {
        CartPage page = new CartPage(driver);
        driver.get("https://www.saucedemo.com/cart.html");
        return page;
    }

    public CartPage(WebDriver driver) { this.driver = driver; }

    public Boolean checkoutStatus() {
        return findTotal(checkoutBtn) > 0;
    }

    public void checkout() {
        click(checkoutBtn);
    }

    public String getItemQuantity() {
        return findBy(cartQuantity).getText();
    }

    public Integer totalItemTypes() {
        List<WebElement> totalItems = listAll(cartItem);
        System.out.println(totalItems.size() + " item types found in cart!\n");
        return totalItems.size();
    }

    public Float itemTotalCalculator() throws NumberFormatException {
        List<WebElement> newList = listAll(inventoryPrice);
        List<String> allText = new ArrayList<>();
        float sum = 0;
        for (int i = 0; i < newList.size(); i++) {
            allText.add(newList.get(i).getText().replace("$", ""));
            System.out.println(allText.get(i));
            float result = Float.parseFloat(allText.get(i));
            /*DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            df.format(result);*/
            sum += result;
        }
        System.out.println("CartPage.itemTotalCalculator() = " + sum);
        return sum;
    }

    public Double itemPriceCalculator() {
        String number = itemCount();
        double result = Double.parseDouble(number);
        System.out.println("itemPriceCalculator() = " + result);
        return result;
    }

    public void cancel() {
        click(continueShopping);
    }
}
