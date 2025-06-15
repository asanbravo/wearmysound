package com.example.demo.spotify;

import com.example.demo.model.Track;
import com.example.demo.port.out.RecentTracksPort;
import com.example.demo.spotify.dto.RecentTracksDto;
import com.example.demo.spotify.mapper.SpotifyTrackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Adaptador REST que implementa RecentTracksPort.
 * Convierte los DTO de Spotify a objetos de dominio con el mapper estático.
 */
@Component
@RequiredArgsConstructor
public class SpotifyTrackClientImpl implements RecentTracksPort {

    private final RestTemplate restTemplate;

    @Value("${spotify.recent-tracks-url}")
    private String recentTracksUrl;

    @Override
    public List<Track> fetchRecentTracks(String userId, int limit) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-User-Id", userId);  // el interceptor leerá esto
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        System.out.println("Interceptor – userId= " + userId);
        /* 1️⃣  /recently-played */
        RecentTracksDto recentDto = restTemplate.exchange(
                recentTracksUrl.replace("{limit}", String.valueOf(limit)),
                HttpMethod.GET,
                entity,
                RecentTracksDto.class
        ).getBody();

        if (recentDto == null || recentDto.getItems().isEmpty()) {
            return List.of();
        }

        System.out.println("Spotify – fetched " +
                recentDto.getItems().get(0).getTrack().getName());

        /* 2️⃣  mapear a dominio sin audio-features */
        return SpotifyTrackMapper.toDomain(recentDto);
    }
}