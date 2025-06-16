// src/main/java/com/demo/stepdefs/spotify/recommendation/WhenTheUserRequestOutfitRecommendationJavaStepDef.java
package com.demo.stepdefs.spotify.recommendation;

import com.demo.ScenarioContext;
import io.cucumber.java.en.When;
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import static io.restassured.RestAssured.*;

@RequiredArgsConstructor
public class WhenTheUserRequestOutfitRecommendationJavaStepDef {

    private final ScenarioContext ctx;

    @Value("${test.base-url}")
    String baseUrl;

    @When("the user requests an outfit recommendation")
    public void userRequestsRecommendation() {
        SessionFilter session = (SessionFilter) ctx.get("SESSION");
        System.out.println("the user requests an outfit recommendation: " + session.getSessionId());


        System.out.println("=== STEP: /outfits/recommendation ===");
        System.out.println("SESSION ID: " + session.getSessionId());
        System.out.println("Base URL  : " + baseUrl);

        Response response = given()
                .baseUri(baseUrl)
                .filter(session)
                .accept("application/json")
                .redirects().follow(false)
                .log().method().log().uri().log().headers().log().cookies()
                .when()
                .get("/outfits/recommendation")
                .then()
                .log().status().log().headers().log().body()
                .extract().response();

        System.out.println("→ Final Status : " + response.getStatusCode());
        System.out.println("→ Final Headers: " + response.getHeaders());
        System.out.println("→ Final Body   : " + response.getBody().asString());

        ctx.put("response", response);
    }
}