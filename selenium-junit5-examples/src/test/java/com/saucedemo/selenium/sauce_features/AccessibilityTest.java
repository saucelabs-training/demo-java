package com.saucedemo.selenium.sauce_features;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.saucedemo.selenium.TestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class AccessibilityTest extends TestBase {

  @BeforeEach
  public void setup(TestInfo testInfo) {
    startChromeSession(testInfo);
  }

  @DisplayName("Axe Accessibility Test - Login Page")
  @Test
  public void loginPageAccessibilityTest() {
    driver.navigate().to("https://www.saucedemo.com");

    AxeBuilder axeBuilder = new AxeBuilder();
    Results accessibilityResults = axeBuilder.analyze(driver);

    // Better assertion with detailed violation information
    List<Rule> violations = accessibilityResults.getViolations();

    if (!violations.isEmpty()) {
      violations.forEach(violation -> {
        System.out.println("Violation: " + violation.getId());
        System.out.println("Impact: " + violation.getImpact());
        System.out.println("Description: " + violation.getDescription());
        System.out.println("Help: " + violation.getHelp());
        System.out.println("Help URL: " + violation.getHelpUrl());
        System.out.println("---");
      });
    }

    Assertions.assertEquals(0, violations.size(),
            "Expected no accessibility violations, but found: " + violations.size());
  }

  @DisplayName("Axe Accessibility Test - Products Page (Authenticated)")
  @Test
  public void productsPageAccessibilityTest() {
    driver.navigate().to("https://www.saucedemo.com");

      // Login first
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    // Wait for products page to load
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_list")));

    AxeBuilder axeBuilder = new AxeBuilder();
    Results results = axeBuilder.analyze(driver);

    assertAccessibilityResults(results);
  }

  @DisplayName("Axe Accessibility Test - Specific WCAG Level")
  @Test
  public void wcagAAComplianceTest() {
    driver.navigate().to("https://www.saucedemo.com");

    AxeBuilder axeBuilder = new AxeBuilder()
            .withTags(Arrays.asList("wcag2a", "wcag2aa")); // Test only WCAG 2.0 Level A and AA

    Results results = axeBuilder.analyze(driver);
    assertAccessibilityResults(results);
  }

  @DisplayName("Axe Accessibility Test - Specific Rules")
  @Test
  public void specificRulesTest() {
    driver.navigate().to("https://www.saucedemo.com");

    AxeBuilder axeBuilder = new AxeBuilder()
            .withOnlyRules(Arrays.asList("color-contrast", "label", "image-alt"));

    Results results = axeBuilder.analyze(driver);
    assertAccessibilityResults(results);
  }

  @DisplayName("Axe Accessibility Test - Exclude Elements")
  @Test
  public void excludeElementsTest() {
    driver.navigate().to("https://www.saucedemo.com");

    AxeBuilder axeBuilder = new AxeBuilder()
            .exclude(".bot_column"); // Exclude specific elements if needed

    Results results = axeBuilder.analyze(driver);
    assertAccessibilityResults(results);
  }

  @DisplayName("Axe Accessibility Test - Cart Page")
  @Test
  public void cartPageAccessibilityTest() {
    driver.navigate().to("https://www.saucedemo.com");

    // Login and add item to cart
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("inventory_list")));

    driver.findElement(By.cssSelector(".inventory_item:first-child .btn_inventory")).click();
    driver.findElement(By.className("shopping_cart_link")).click();

    AxeBuilder axeBuilder = new AxeBuilder();
    Results results = axeBuilder.analyze(driver);

    assertAccessibilityResults(results);
  }

  // Helper method for consistent assertions
  private void assertAccessibilityResults(Results results) {
    List<Rule> violations = results.getViolations();

    if (!violations.isEmpty()) {
      StringBuilder message = new StringBuilder("Accessibility violations found:\n");
      violations.forEach(violation -> {
        message.append(String.format("- %s (%s): %s\n",
                violation.getId(),
                violation.getImpact(),
                violation.getDescription()));
      });
      Assertions.fail(message.toString());
    }
  }

  @DisplayName("Axe Accessibility Test with HTML Report")
  @Test
  public void accessibilityTestWithReport() throws IOException {
    driver.navigate().to("https://www.saucedemo.com");

    AxeBuilder axeBuilder = new AxeBuilder();
    Results results = axeBuilder.analyze(driver);

    // Generate HTML report
    String reportPath = "target/axe-reports/accessibility-report.html";
    Files.createDirectories(Paths.get("target/axe-reports"));

    String htmlReport = generateHtmlReport(results);
    Files.write(Paths.get(reportPath), htmlReport.getBytes());

    System.out.println("Accessibility report generated: " + reportPath);

    Assertions.assertEquals(0, results.getViolations().size());
  }

  private String generateHtmlReport(Results results) {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html><html><head><title>Accessibility Report</title>");
    html.append("<style>body{font-family:Arial;margin:20px;} .violation{border:1px solid #ccc;padding:10px;margin:10px 0;} .critical{background:#ffebee;} .serious{background:#fff3e0;}</style>");
    html.append("</head><body><h1>Accessibility Test Results</h1>");

    html.append("<h2>Summary</h2>");
    html.append("<p>Violations: ").append(results.getViolations().size()).append("</p>");
    html.append("<p>Passes: ").append(results.getPasses().size()).append("</p>");

    html.append("<h2>Violations</h2>");
    results.getViolations().forEach(violation -> {
      html.append("<div class='violation ").append(violation.getImpact()).append("'>");
      html.append("<h3>").append(violation.getId()).append("</h3>");
      html.append("<p><strong>Impact:</strong> ").append(violation.getImpact()).append("</p>");
      html.append("<p><strong>Description:</strong> ").append(violation.getDescription()).append("</p>");
      html.append("<p><strong>Help:</strong> <a href='").append(violation.getHelpUrl()).append("'>").append(violation.getHelp()).append("</a></p>");
      html.append("</div>");
    });

    html.append("</body></html>");
    return html.toString();
  }
}

