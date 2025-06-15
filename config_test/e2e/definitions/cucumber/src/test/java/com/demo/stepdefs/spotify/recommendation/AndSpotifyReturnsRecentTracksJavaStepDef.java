package com.demo.stepdefs.spotify.recommendation;

import io.cucumber.java.en.And;

public class AndSpotifyReturnsRecentTracksJavaStepDef {

    @And("Spotify returns recent tracks with high energy and high valence")
    @And("Spotify returns recent tracks with low energy and medium valence")
    public void spotify_returns_stub_data() {
        // No-op: WireMock ya tiene los JSON en /mappings
    }
}