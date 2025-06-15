package com.example.demo.spotify.adapter;

import com.example.demo.model.SpotifyToken;
import com.example.demo.port.out.SpotifyTokenPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("test")                          // solo e2e + unit
public class InMemorySpotifyTokenStub implements SpotifyTokenPort {

    private final Map<String, SpotifyToken> map = new ConcurrentHashMap<>();

    public InMemorySpotifyTokenStub() {
        map.put("test", SpotifyToken.of("test", "stub-token", "stub-refresh", 3600));
    }

    @Override
    public String obtainValidAccessToken(String userId) {
        SpotifyToken t = map.get(userId);
        return t != null ? t.getAccessToken() : "stub-token";
    }

    @Override
    public void saveToken(SpotifyToken token) {
        map.put(token.getUserId(), token);
    }
}