package com.demo.stepdefs.spotify.authorization;

import com.demo.ScenarioContext;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ThenTheSystemStoresAValidAccessTokenJavaStepDef {

    @Autowired
    private ScenarioContext scenarioContext;

    @Then("the system stores a valid access token")
    public void system_stores_valid_token() {
        ResponseEntity<String> res = (ResponseEntity<String>) scenarioContext.get("response");
        System.out.println("Response body: " + res.getBody());
        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        assertThat((String) JsonPath.read(res.getBody(), "$.accessToken"))
                .isEqualTo("A_TEST_ACCESS_TOKEN");
        assertThat((String) JsonPath.read(res.getBody(), "$.refreshToken"))
                .isEqualTo("A_TEST_REFRESH_TOKEN");
        assertThat((Integer) JsonPath.read(res.getBody(), "$.expiresInSeconds"))
                .isEqualTo(3600);
    }
}