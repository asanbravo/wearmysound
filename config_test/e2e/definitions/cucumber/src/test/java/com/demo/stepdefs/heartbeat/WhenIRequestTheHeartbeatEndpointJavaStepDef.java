package com.demo.stepdefs.heartbeat;


import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import com.demo.restClients.system.HealthClient;
import com.demo.ScenarioContext;
import com.demo.restClients.system.HealthResponse;
import org.springframework.http.ResponseEntity;

public class WhenIRequestTheHeartbeatEndpointJavaStepDef {

   @Autowired
  private ScenarioContext scenarioContext;

  @Autowired
  private HealthClient healthClient;

  @When("I request the heartbeat endpoint")
  public void i_request_a_heartbeat_endpoint() {
    ResponseEntity<HealthResponse> responseEntity = healthClient.checkHealth();
    scenarioContext.put("responseEntity", responseEntity);
  }
  

}
