package com.example.demo.spotify.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrations,
            OAuth2AuthorizedClientService clientService) {

        // 1) Construimos el manager usando tu OAuth2AuthorizedClientService
        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrations,
                        clientService
                );

        // 2) Definimos qué flujos soportamos (code, refresh, client_credentials…)
        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .clientCredentials()
                .build();
        manager.setAuthorizedClientProvider(provider);

        return manager;
    }

    @Bean
    public RestTemplate spotifyRestTemplate(
            RestTemplateBuilder builder,
            OAuth2AuthorizedClientManager authorizedClientManager) {

        // Interceptor que inyecta el Bearer token automáticamente
        ClientHttpRequestInterceptor interceptor = (request, body, execution) -> {
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            if (principal != null && principal.isAuthenticated()) {
                OAuth2AuthorizeRequest authRequest = OAuth2AuthorizeRequest
                        .withClientRegistrationId("spotify")
                        .principal(principal)
                        .build();
                OAuth2AuthorizedClient client = authorizedClientManager.authorize(authRequest);
                if (client != null && client.getAccessToken() != null) {
                    request.getHeaders().setBearerAuth(client.getAccessToken().getTokenValue());
                }
            }
            return execution.execute(request, body);
        };

        return builder
                .additionalInterceptors(interceptor)
                .build();
    }
}
