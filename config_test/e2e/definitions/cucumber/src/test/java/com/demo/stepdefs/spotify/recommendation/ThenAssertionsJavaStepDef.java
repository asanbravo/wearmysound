package com.demo.stepdefs.spotify.recommendation;

import com.demo.ScenarioContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ThenAssertionsJavaStepDef {

    private final ScenarioContext ctx;
    private final ObjectMapper om = new ObjectMapper();

    @Then("the system analyses the tracks")
    public void system_analyses_tracks() {
        ResponseEntity<?> resp = (ResponseEntity<?>) ctx.get("response");
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Then("the recommended outfit style must be {string}")
    public void style_must_be(String expected) throws Exception {
        ResponseEntity<String> response = (ResponseEntity<String>) ctx.get("response");
        JsonNode root = om.readTree(response.getBody());
        assertThat(root.get("style").asText()).isEqualToIgnoringCase(expected);
    }

    @Then("the primary colour palette must be {string}")
    public void primary_colour_must_be(String expectedType) throws Exception {
        ResponseEntity<String> response = (ResponseEntity<String>) ctx.get("response");
        JsonNode root = om.readTree(response.getBody());
        String primaryHex = root.at("/palette/primary").asText();

        if (expectedType.equalsIgnoreCase("vibrant"))
            assertThat(primaryHex).isEqualTo("#FF5722");
        else if (expectedType.equalsIgnoreCase("pastel"))
            assertThat(primaryHex).isEqualTo("#B0BEC5");
        else
            throw new AssertionError("Unknown colour type " + expectedType);
    }
}