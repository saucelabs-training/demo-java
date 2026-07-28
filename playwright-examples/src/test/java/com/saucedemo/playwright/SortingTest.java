package com.saucedemo.playwright;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SortingTest extends TestBase {

  @Test
  public void sortByNameAscending() {
    login();
    page.locator("[data-test='product-sort-container']").selectOption("az");

    List<String> actual = itemNames();
    Assertions.assertEquals(sorted(actual, Comparator.naturalOrder()), actual);
  }

  @Test
  public void sortByNameDescending() {
    login();
    page.locator("[data-test='product-sort-container']").selectOption("za");

    List<String> actual = itemNames();
    Assertions.assertEquals(sorted(actual, Comparator.reverseOrder()), actual);
  }

  @Test
  public void sortByPriceLowToHigh() {
    login();
    page.locator("[data-test='product-sort-container']").selectOption("lohi");

    List<Double> actual = itemPrices();
    Assertions.assertEquals(sorted(actual, Comparator.naturalOrder()), actual);
  }

  @Test
  public void sortByPriceHighToLow() {
    login();
    page.locator("[data-test='product-sort-container']").selectOption("hilo");

    List<Double> actual = itemPrices();
    Assertions.assertEquals(sorted(actual, Comparator.reverseOrder()), actual);
  }

  private void login() {
    page.navigate("https://www.saucedemo.com/");
    page.locator("[data-test='username']").fill("standard_user");
    page.locator("[data-test='password']").fill("secret_sauce");
    page.locator("[data-test='login-button']").click();
  }

  private List<String> itemNames() {
    return page.locator(".inventory_item_name").allTextContents();
  }

  private List<Double> itemPrices() {
    return page.locator(".inventory_item_price").allTextContents().stream()
        .map(price -> Double.parseDouble(price.replace("$", "")))
        .collect(Collectors.toList());
  }

  private <T> List<T> sorted(List<T> values, Comparator<T> comparator) {
    return values.stream().sorted(comparator).collect(Collectors.toList());
  }
}
