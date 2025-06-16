package com.demo.stepdefs.spotify.recommendation;

import com.demo.ScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class AndTheUserCompletesTheSpotifyLoginCallback {
    private final ScenarioContext ctx;

    public AndTheUserCompletesTheSpotifyLoginCallback(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @And("the user completes the Spotify login callback")
    public void simulateSpotifyCallback() {
        SessionFilter session = (SessionFilter) ctx.get("SESSION");

        System.out.println("the user completes the Spotify login callback: " + session.getSessionId());

        given()
                .baseUri("http://localhost:8080")
                .filter(session)
                .redirects().follow(false)
                .when()
                .get("/login/oauth2/code/spotify?code=test-code&state=test-state")
                .then()
                .statusCode(302); // Redirige a /outfits/recommendation
    }
}
