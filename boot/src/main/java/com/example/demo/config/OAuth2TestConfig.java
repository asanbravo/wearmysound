// Java
package com.example.demo.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Configuration
@Profile({"docker","local"})
public class OAuth2TestConfig {

    private static final String FIXED_STATE = "test-state";

    @PostConstruct
    public void debug() {
        System.out.println("[DEBUG] OAuth2TestConfig ACTIVE");
    }

    @Bean
    public OAuth2AuthorizationRequestResolver authorizationRequestResolver(
            ClientRegistrationRepository clientRegistrationRepository) {

        DefaultOAuth2AuthorizationRequestResolver defaultResolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository,
                        "/oauth2/authorization"
                );

        return new OAuth2AuthorizationRequestResolver() {

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest original = defaultResolver.resolve(request);
                return modifyAuthorizationRequest(original);
            }

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest original = defaultResolver.resolve(request, clientRegistrationId);
                return modifyAuthorizationRequest(original);
            }

            private OAuth2AuthorizationRequest modifyAuthorizationRequest(OAuth2AuthorizationRequest original) {
                if (original == null) {
                    return null;
                }
                OAuth2AuthorizationRequest modified = OAuth2AuthorizationRequest
                        .from(original)
                        .state(FIXED_STATE)
                        .build();
                System.out.println("[DEBUG2] Resolver modified state: " + modified.getState());
                return modified;
            }
        };
    }
}