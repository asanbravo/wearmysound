package com.demo.stepdefs.spotify.recommendation;

import com.demo.ScenarioContext;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class WhenTheUserAsksForAnOutfitJavaStepDef {

    private final ScenarioContext ctx;
    private final RestTemplate rest = new RestTemplate();

    @Value("${test.base-url}")
    String baseUrl;

    @When("the user asks for an outfit recommendation")
    public void userRequestsRecommendation() {
        ResponseEntity<String> resp = rest.getForEntity(
                baseUrl + "/outfits/recommendation?userId=test", String.class);
        ctx.put("response", resp);
    }
}
