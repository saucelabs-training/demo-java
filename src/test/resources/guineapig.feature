@guineapig
Feature: Sauce Labs Guinea Pig
  
  Scenario: We want to click on the link and verify it takes us to a new page
    Given I am on the Sauce Labs Guinea Pig test page
    When I click on the link
    Then I should see a new page 