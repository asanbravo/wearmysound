package com.demo.stepdefs.spotify.recommendation;

import com.demo.ScenarioContext;
import com.demo.config.AuthHelper;
import io.cucumber.java.en.Given;
import io.restassured.filter.session.SessionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class GivenTheUserHasAValidSpotifyTokenJavaStepDef {


    @Autowired
    private ScenarioContext scenarioContext;

    @Given("the user has a valid Spotify access token")
    public void userHasValidToken() {
        SessionFilter session = AuthHelper.loginWithSpotify();
        System.out.println("Session ID the user has a valid Spotify access token: " + session.getSessionId());
        scenarioContext.put("SESSION", session);
        System.out.println("Session ID the user after up: " + scenarioContext.get("SESSION"));

    }
}
