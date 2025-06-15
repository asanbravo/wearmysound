@management @heartbeat
Feature: Heartbeat check

  @ID-1
  Scenario: [ID-1] Application is up
    When I request the heartbeat endpoint
    Then the response status must be 200
    And the response must contain "UP"