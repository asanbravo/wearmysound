package com.demo.stepdefs.spotify.authorization;

import com.demo.ScenarioContext;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WhenTheUserRequestsToConnectJavaStepDef {
    @Autowired
    private ScenarioContext scenarioContext;

    private final RestTemplate rest = new RestTemplate();

    @Value("${test.base-url}")
    private String baseUrl;

    @When("the user requests to connect their Spotify account")
    public void user_requests_connection() {
        System.out.println("baseUrl = " + baseUrl);
        ResponseEntity<String> response =
                rest.getForEntity(baseUrl + "/authorize", String.class);
        scenarioContext.put("response", response);
    }
}
