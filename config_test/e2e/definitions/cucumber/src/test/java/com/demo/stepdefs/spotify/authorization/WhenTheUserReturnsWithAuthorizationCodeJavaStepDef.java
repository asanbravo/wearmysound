package com.demo.stepdefs.spotify.authorization;

import com.demo.ScenarioContext;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WhenTheUserReturnsWithAuthorizationCodeJavaStepDef {

    @Autowired
    private ScenarioContext scenarioContext;

    private final RestTemplate rest = new RestTemplate();

    @Value("${test.base-url}")
    private String baseUrl;

    @When("the user returns to the application with an authorisation code")
    public void user_returns_with_code() {
        String code = "TEST_CODE";                          // any dummy value
        ResponseEntity<String> response =
                rest.getForEntity(baseUrl + "/callback?code=" + code, String.class);
        scenarioContext.put("response", response);
    }
}
