package com.demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import com.demo.restClients.system.HealthClient;

@Configuration
@ComponentScan({"com.demo"})
public class SpringConfiguration {


  @Value("${service.endpoint.demo.root}")
  private String rootUrl;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(List.of(new MappingJackson2HttpMessageConverter()));
  }

  @Bean
  public HealthClient healthClient() {
    return new HealthClient(rootUrl, restTemplate());
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
