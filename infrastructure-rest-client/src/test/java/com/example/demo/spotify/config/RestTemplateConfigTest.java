package com.example.demo.spotify.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;
import com.example.demo.spotify.config.TestConfig;

import static org.assertj.core.api.Assertions.assertThat;

class RestTemplateConfigTest {

    @Test
    void restTemplateBeanExists() {
        try (var ctx = new AnnotationConfigApplicationContext(RestTemplateConfig.class, TestConfig.class)) {
            RestTemplate restTemplate = ctx.getBean(RestTemplate.class);
            assertThat(restTemplate).isNotNull();
        }
    }
}