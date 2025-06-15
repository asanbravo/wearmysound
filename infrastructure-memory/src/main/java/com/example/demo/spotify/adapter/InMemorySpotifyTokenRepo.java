package com.example.demo.spotify.adapter;

import com.example.demo.model.SpotifyToken;
import com.example.demo.port.out.SpotifyTokenPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("!test")                         // cualquier perfil real
public class InMemorySpotifyTokenRepo implements SpotifyTokenPort {

    private final Map<String, SpotifyToken> db = new ConcurrentHashMap<>();

    @Override
    public String obtainValidAccessToken(String userId) {
        SpotifyToken t = db.get(userId);
        return t != null ? t.getAccessToken() : null;
    }

    @Override
    public void saveToken(SpotifyToken token) {
        db.put(token.getUserId(), token);
    }
}