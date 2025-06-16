Feature: Recommend outfit based on Spotify mood
  As a Wear My Sound user
  I want to receive outfit suggestions tuned to my music vibe
  So that I feel confident and in sync with my mood

  Background:
    Given the user has a valid Spotify access token
    And the user completes the Spotify login callback

  @ID-2
  Scenario: [ID-2] Energetic happy mood yields urban vibrant outfit
    Given the Spotify API returns a list of energetic/happy tracks
    And the Audio Features service returns high energy, high valence data
    When the user requests an outfit recommendation
    Then the HTTP status should be 200
    And the JSON response should contain:
      | style            | urban   |
      | primaryPalette   | vibrant |

  @ID-3
  Scenario: [ID-3] Chill mood yields casual pastel outfit
    Given the Spotify API returns a list of chill tracks
    And the Audio Features service returns low energy, low valence data
    When the user requests an outfit recommendation
    Then the recommended style should be "CASUAL"
    And the primary color should be "#B0BEC5"
    And the outfit should include:
      | category | description           |
      | hoodie   | Soft cotton hoodie    |
      | jeans    | Relaxed-fit jeans     |