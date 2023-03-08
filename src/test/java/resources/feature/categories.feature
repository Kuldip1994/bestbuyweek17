Feature: Testing various of request on Best Buy Categories Application

  Scenario: Check if Categories list can be accessed by users
    When user sends Get request to list endpoints
    Then user should get status code 200 in return

  Scenario Outline: Create a new categories and verify if it is added
    When I create a new categories with name "<name>" id "<id>"
    And I verify that the categories with name
    Examples:
      | name        | id  |
      | TV&Speakers | no1 |
      | Home Cinema | no2 |

  Scenario: update the categories by name and verify
    When I updated the categories with "<name>"

  Scenario: check if categories has been deleted
    When I deleted the categories by id
    Then I should verify that the categories is deleted







