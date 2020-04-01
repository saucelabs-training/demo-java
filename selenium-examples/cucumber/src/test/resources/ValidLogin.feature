Feature: Valid Login

  Scenario: Login as valid user
    Given I go to the login page
    When I login as a valid user
    Then The item list is displayed