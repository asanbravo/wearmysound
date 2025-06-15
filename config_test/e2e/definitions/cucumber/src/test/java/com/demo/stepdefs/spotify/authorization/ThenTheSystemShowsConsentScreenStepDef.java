package com.demo.stepdefs.spotify.authorization;

import com.demo.ScenarioContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Then;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ThenTheSystemShowsConsentScreenStepDef {

    private final ScenarioContext scenarioContext;

    public ThenTheSystemShowsConsentScreenStepDef(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Then("the system shows the Spotify consent screen")
    public void system_shows_consent_screen() {
        ResponseEntity<String> res = (ResponseEntity<String>) scenarioContext.get("response");
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        String url = JsonPath.read(res.getBody(), "$.url");
        assertThat(url).startsWith("https://accounts.spotify.com/authorize");
    }
}