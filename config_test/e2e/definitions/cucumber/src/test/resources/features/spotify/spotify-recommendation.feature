Feature: Recommend an outfit from my recent Spotify tracks
  As a Wear-my-Sound user
  I want the system to analyse the mood of my latest songs
  So that I receive outfit suggestions that match my current vibe

  Background:
    Given the user has already linked their Spotify account

  @ID-4
  Scenario: [ID-4] Energetic / happy tracks yield an urban outfit
    When the user asks for an outfit recommendation
    Then the system analyses the tracks
    And  the recommended outfit style must be "urban"
    And  the primary colour palette must be "vibrant"

  @ID-5
  Scenario: [ID-5] Chill tracks yield a casual outfit
    When the user asks for an outfit recommendation
    Then the system analyses the tracks
    And  the recommended outfit style must be "casual"
    And  the primary colour palette must be "pastel"
