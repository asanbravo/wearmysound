package com.example.demo.port.out;


import com.example.demo.model.SpotifyToken;

public interface SpotifyTokenPort {

    /**
     * Devuelve el access-token aún válido para el usuario.
     * Si expira, el adaptador se encargará de refrescarlo.
     */
    String obtainValidAccessToken(String userId);

    /** Persiste o actualiza el token del usuario */
    void saveToken(SpotifyToken token);
}