Feature: Bag

  Scenario: Removing a product from the Bag
    Given there are products in the bag
    When I remove a product
    Then the product is removed from the bag

  Scenario: Adding quantity to a product in the Bag
    Given there are products in the bag
    When I add quantity
    Then product quantity is increased

  Scenario: Removing quantity from a product in the Bag
    Given there are products in the bag
    When I remove quantity
    Then product quantity is removed from the bag
