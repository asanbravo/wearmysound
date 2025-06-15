package com.demo.restClients.system;

import java.util.Map;

import lombok.Data;
import org.springframework.boot.actuate.health.Status;

@Data
public class HealthResponse {

  private Status status;

  private Map<String, Object> details;
}
