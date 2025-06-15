package com.example.demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter                       // ← genera getAccessToken(), getRefreshToken()...
@Builder(toBuilder = true)   // ← por si un día quieres copiar y modificar
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotifyToken {

    private final String userId;
    private final String accessToken;
    private final String refreshToken;
    private final long   expiresIn;   // 3600 típico
    private final long   obtainedAtEpochSeconds;

    /** Factory estático usado en los tests/ stubs. */
    public static SpotifyToken of(String userId,
                                  String accessTok,
                                  String refreshTok,
                                  long expires) {
        return new SpotifyToken(
                userId,
                accessTok,
                refreshTok,
                expires,
                /* obtainedNow */ System.currentTimeMillis() / 1000);
    }

    /** Devuelve true si el access-token ha caducado. */
    public boolean isExpired() {
        long now = System.currentTimeMillis() / 1000;
        return now >= obtainedAtEpochSeconds + expiresIn;
    }
}
