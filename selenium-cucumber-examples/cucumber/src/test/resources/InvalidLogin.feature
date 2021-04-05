Feature: Invalid Login

  Scenario: Login as invalid user
    Given I go to the login page
    When I login as an invalid user
    Then The item list is not displayed