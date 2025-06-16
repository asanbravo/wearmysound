package com.demo.stepdefs.spotify.recommendation;

import io.cucumber.java.en.Given;

public class GivenTheSpotifyAPIReturnsJavaStepDef {


    @Given("the Spotify API returns a list of chill tracks")
    public void spotifyReturnsChillTracks() {
        // stub loaded from common/spotify-recent-tracks-chill.json or ID-5
    }

    @Given("the Spotify API returns an empty track list")
    public void spotifyReturnsEmptyList() {
        // stub loaded from common/spotify-recent-tracks-empty.json or ID-7
    }

    @Given("the Spotify API returns a list of energetic\\/happy tracks")
    public void theSpotifyAPIReturnsAListOfEnergeticHappyTracks() {
    }

    @Given("the Audio Features service returns high energy, high valence data")
    public void audioFeaturesHigh() {
        // stub loaded from common/audio-features-high.json or ID-4
    }

    @Given("the Audio Features service returns low energy, low valence data")
    public void audioFeaturesLow() {
        // stub loaded from common/audio-features-low.json or ID-5
    }

    @Given("the Audio Features service returns 500 Internal Server Error")
    public void audioFeaturesError() {
        // stub loaded from mocks/ID-8/audio-features-error.json
    }
}
