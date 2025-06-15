package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers("/actuator/**").permitAll()
                            .requestMatchers("/outfits/recommendation").authenticated()
                            .anyRequest().permitAll()
                    )
                    .oauth2Login(oauth2 -> oauth2
                            .defaultSuccessUrl("/outfits/recommendation", true)
                    )
                    // aquí no usamos .and(), simplemente:
                    .oauth2Client(Customizer.withDefaults());

            return http.build();
        }
    }
