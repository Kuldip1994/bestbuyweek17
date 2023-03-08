Feature: Testing different types of stores which is offered by Bust Buy

  Scenario: check if store list can be accessed by user
    When user send a Get request to list endpoints
    Then user should get status code 200

  Scenario: creating new stores and verify if it has been added
    When creating new store with new "<name>" "<type>" "<address>" "<address2>" "<city>" "<state>" "<zip>" "<hours>"
    Then I verify that store with name is created

  Scenario: check if the name of new store has been updated and verify updated information
    When New store is updated with new "<name>"

  Scenario: check if the store has been deleted by id and verify if deleted
    When I have deleted store by id
    Then I verify that store is deleted