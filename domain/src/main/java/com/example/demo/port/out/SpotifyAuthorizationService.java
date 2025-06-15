package com.example.demo.port.out;

import com.example.demo.model.SpotifyToken;

public interface SpotifyAuthorizationService {
    SpotifyToken exchangeCodeForToken(String code);
}
