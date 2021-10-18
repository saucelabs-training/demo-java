package com.saucedemo.selenium.se4newfeatures;

import com.saucelabs.saucebindings.junit5.SauceBaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
public class AttributePropertyTest extends SauceBaseTest {

    /**
     * Property and Attribute often return the same value (especially in Java, since values are converted to Strings,
     * but they are different
     * attribute is defined in html spec: https://dom.spec.whatwg.org/#concept-element-attribute
     * property is defined in ecma spec: https://262.ecma-international.org/5.1/#sec-4.3.26
     *
     * WebElement#getAttribute guesses which value you want from an element's attribute or property value and returns that
     *
     * Since this doesn't make sense in a specification, w3c defines 2 new endpoints, made available in Selenium as:
     * WebElement#getDomProperty and WebElement#getDomAttribute
     *
     * The old behavior with the existing method is still available, but executes a large javascript blob
     * New behavior should be preferred for performance and preciseness
     */

    @DisplayName("getDomProperty returns false, getDomAttribute returns null")
    @Test
    public void domPropertyReturnsFalseInsteadOfNullForBoolean() {
        driver.navigate().to("http://watir.com/examples/forms_with_input_elements.html");
        WebElement element = driver.findElement(By.id("new_user_interests_books"));

        Assertions.assertEquals("true", element.getAttribute("checked"));
        Assertions.assertEquals("true", element.getDomProperty("checked"));

        element.click();

        Assertions.assertNull(element.getAttribute("checked"));
        Assertions.assertEquals("false", element.getDomProperty("checked"));
    }

    @DisplayName("getDomProperty Boolean result updates, getDomAttribute does not")
    @Test
    public void attributePropertyDoesNotUpdateBoolean() {
        driver.navigate().to("http://watir.com/examples/forms_with_input_elements.html");
        WebElement element = driver.findElement(By.id("new_user_interests_books"));

        Assertions.assertEquals("true", element.getAttribute("checked"));
        Assertions.assertEquals("true", element.getDomAttribute("checked"));
        Assertions.assertEquals("true", element.getDomProperty("checked"));

        element.click();

        Assertions.assertNull(element.getAttribute("checked"));
        Assertions.assertEquals("false", element.getDomProperty("checked"));
        Assertions.assertEquals("true", element.getDomAttribute("checked"));
    }

    @DisplayName("getDomProperty String result updates, getDomAttribute does not")
    @Test
    public void attributePropertyDoesNotUpdateString() {
        driver.navigate().to("http://watir.com/examples/forms_with_input_elements.html");
        WebElement element = driver.findElement(By.id("new_user_occupation"));

        Assertions.assertEquals("Developer", element.getAttribute("value"));
        Assertions.assertEquals("Developer", element.getDomAttribute("value"));
        Assertions.assertEquals("Developer", element.getDomProperty("value"));

        element.clear();
        element.sendKeys("Engineer");

        Assertions.assertEquals("Engineer", element.getAttribute("value"));
        Assertions.assertEquals("Developer", element.getDomAttribute("value"));
        Assertions.assertEquals("Engineer", element.getDomProperty("value"));
    }

    @DisplayName("getDomAttribute is what is in the DOM, getDomProperty may includes parsing")
    @Test
    public void urlValues() {
        driver.navigate().to("http://watir.com/examples/non_control_elements.html");
        WebElement element = driver.findElement(By.id("link_3"));

        Assertions.assertEquals("http://watir.com/examples/forms_with_input_elements.html", element.getDomProperty("href"));
        Assertions.assertEquals("forms_with_input_elements.html", element.getDomAttribute("href"));
    }

    @DisplayName("getDomProperty is case sensitive, getDomAttribute is not")
    @Test
    public void caseSensitivity() {
        driver.navigate().to("http://watir.com/examples/forms_with_input_elements.html");
        WebElement element = driver.findElement(By.name("new_user_email"));

        Assertions.assertEquals("new_user_email", element.getDomAttribute("nAme"));
        Assertions.assertNull(element.getDomProperty("nAme"));
    }

    @DisplayName("property className is equivalent to attribute class")
    @Test
    public void className() {
        driver.navigate().to("http://watir.com/examples/forms_with_input_elements.html");
        WebElement element = driver.findElement(By.id("new_user_first_name"));

        Assertions.assertEquals("name", element.getDomProperty("className"));
        Assertions.assertEquals("name", element.getDomAttribute("class"));
    }
}
