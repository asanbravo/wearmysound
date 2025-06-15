package com.demo.restClients.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.demo.restClients.system.HealthResponse;

public class HealthClient {

  @Value("${test.base-url}")
  private String serviceRoot;

  private final RestTemplate restTemplate;

  public HealthClient(
      String serviceRoot,
      RestTemplate restTemplate
  ) {
    this.restTemplate = restTemplate;
    this.serviceRoot = serviceRoot;
  }

  public ResponseEntity<HealthResponse> checkHealth() {
    System.out.println("serviceRoot = " + serviceRoot);
      return restTemplate.getForEntity(serviceRoot + "/actuator/health", HealthResponse.class);
  }

}
