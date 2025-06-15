// Lenguaje: java
package com.example.demo.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpotifyTokenTest {


    @Test
    void isExpired_returns_true_when_now_is_past_expiry() {
        SpotifyToken token = SpotifyToken.of("user1", "access", "refresh", 3600)
                .toBuilder()
                .obtainedAtEpochSeconds(0) // Valor antiguo para forzar expiración.
                .build();
        assertTrue(token.isExpired());
    }

    @Test
    void isExpired_returns_false_when_not_past_expiry() {
        long now = System.currentTimeMillis() / 1000;
        SpotifyToken token = SpotifyToken.of("A", "R", String.valueOf(3600), now);
        assertThat(token.isExpired()).isFalse();
    }
}