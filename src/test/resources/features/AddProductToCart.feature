Feature: Buying the Iphone 16


  @TC-001
  Scenario: Add the product to cart
    Given I go to trendyol
    When I put the product name in the search box
    And I choose the product
    And I add the cart
    And I go to cart
    Then I should see the product in the cart

