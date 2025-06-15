Feature: Connect a Spotify account
  As a Wear-my-Sound user
  I want to link my personal Spotify account
  So that the application can analyse my music and moods

  @ID-2
  Scenario: [ID-2] The user starts the connection process
    Given the user has not linked Spotify yet
    When the user requests to connect their Spotify account
    Then the system shows the Spotify consent screen

  @ID-3
  Scenario: [ID-3] The user completes the connection process
    Given the user has granted permissions in Spotify
    When the user returns to the application with an authorisation code
    Then the system stores a valid access token
