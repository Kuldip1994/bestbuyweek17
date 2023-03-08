Feature: Testing different types of products which are offered by Best Buy

  Scenario: check if product list can be accessed by users
    When User sends a Get request to list endpoints
    Then user should get status code 200

  Scenario: creating new product and verify if it has been added
    When I create a new product by providing information "<name>"  "<type>" "<model>" "<description>" "<Url>" "<Image>" <"Manufacturer>" "<Upc>"
    Then I verify that the product with name is created

  Scenario: check if the name of new product has been updated and verify updated information
    When new product is updated with "<name>"

    Scenario: Check if new product has been deleted by id and verify if deleted
      When I have deleted product by id
      Then I verify that product is deleted
