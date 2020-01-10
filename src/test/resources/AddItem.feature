Feature: Add Item To Cart

  Scenario: Adding one item to a cart
    Given I am on the inventory page
    When I add 1 item to the cart
    Then I have 1 item in my cart

  Scenario: Adding two items to a cart
    Given I am on the inventory page
    When I add 2 item to the cart
    Then I have 2 items in my cart