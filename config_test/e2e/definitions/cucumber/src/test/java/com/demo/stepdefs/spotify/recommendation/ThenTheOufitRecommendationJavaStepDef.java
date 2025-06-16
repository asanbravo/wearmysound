package com.demo.stepdefs.spotify.recommendation;

import com.demo.ScenarioContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ThenTheOufitRecommendationJavaStepDef {

    private final ScenarioContext context;

    private Map<String, Object> parseResponseJson() throws Exception {
        Response response = (Response) context.get("response");
        String body = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, new TypeReference<>() {});
    }

    @Then("the recommended style should be {string}")
    public void theRecommendedStyleShouldBe(String expectedStyle) throws Exception {
        Map<String, Object> response = parseResponseJson();
        assertThat(response.get("style")).isEqualTo(expectedStyle);
    }

    @Then("the primary color should be {string}")
    public void thePrimaryColorShouldBe(String expectedColor) throws Exception {
        Map<String, Object> response = parseResponseJson();
        Map<String, String> palette = (Map<String, String>) response.get("palette");
        assertThat(palette.get("primary")).isEqualTo(expectedColor);
    }

    @Then("the outfit should include:")
    public void theOutfitShouldInclude(DataTable table) throws Exception {
        Map<String, Object> response = parseResponseJson();
        List<Map<String, Object>> garments = (List<Map<String, Object>>) response.get("garments");

        List<Map<String, String>> expected = table.asMaps();

        for (Map<String, String> expectedGarment : expected) {
            assertThat(garments).anyMatch(actual ->
                    expectedGarment.get("category").equals(actual.get("category")) &&
                            expectedGarment.get("description").equals(actual.get("description"))
            );
        }
    }
}