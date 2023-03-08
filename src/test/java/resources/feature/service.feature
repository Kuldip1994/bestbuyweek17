Feature: Testing different types of Services which is offered by Bust Buy


  Scenario: Check if services list can be accessed by users
    When user sends a Get request to list endpoints
    Then user should get status code 200

  Scenario: Creating new service and verify if it has been added
    When Created new service with new "<name>"
    Then I verify that service with name is created

  Scenario: Check if the name of new service has been updated and verify updated information
    When New service is updated with new name "<name>"

    Scenario: Check if the new service has been deleted by id and verify if deleted
      When I have deleted service by id
      Then I verify that service is deleted