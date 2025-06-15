package com.demo.stepdefs.heartbeat;

import static org.assertj.core.api.Assertions.assertThat;


import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import com.demo.restClients.system.HealthResponse;
import com.demo.ScenarioContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ThenTheResponseStatusMustJavaStepDef {

  @Autowired
  private ScenarioContext scenarioContext;

  @Then("the response status must be 200")
  public void the_response_must_be_twohundred() {
    ResponseEntity<HealthResponse> responseEntity = (ResponseEntity<HealthResponse>) scenarioContext.get("responseEntity");
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @And("the response must contain \"UP\"")
  public void the_response_must_contain_up() {
    ResponseEntity<HealthResponse> responseEntity = (ResponseEntity<HealthResponse>) scenarioContext.get("responseEntity");
    HealthResponse healthResponse = responseEntity.getBody();
    assertThat(healthResponse).isNotNull();
    assertThat(healthResponse.getStatus()).isEqualTo(Status.UP);
  }

}