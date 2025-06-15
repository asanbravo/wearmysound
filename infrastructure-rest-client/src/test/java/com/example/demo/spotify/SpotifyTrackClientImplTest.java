package com.example.demo.spotify;


import com.example.demo.model.Track;
import com.example.demo.spotify.dto.AudioFeaturesDto;
import com.example.demo.spotify.dto.RecentTracksDto;
import com.example.demo.spotify.mapper.SpotifyTrackMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SpotifyTrackClientImpl}.
 */
class SpotifyTrackClientImplTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private SpotifyTrackClientImpl client;

    // URLs con placeholders tal y como llegarían vía @Value
    private static final String RECENT_URL_TMPL  = "http://api/recent?limit={limit}";
    private static final String FEATS_URL_TMPL   = "http://api/features?ids={ids}";

    @BeforeEach
    void init() {
        client = new SpotifyTrackClientImpl(restTemplate);
        // inyectamos los @Value manualmente
        ReflectionTestUtils.setField(client, "recentTracksUrl",  RECENT_URL_TMPL);
        ReflectionTestUtils.setField(client, "audioFeaturesUrl", FEATS_URL_TMPL);
    }

    /* ---------------------------------------------------------------
       1) Happy-path: lista de 1 pista con sus audio-features
       --------------------------------------------------------------- */
    @Test
    @DisplayName("fetchRecentTracks – llama a los dos endpoints y mapea a dominio")
    void fetchRecentTracks_ok() {
        /* ---------- stub /recent ---------- */
        RecentTracksDto recentDto = new RecentTracksDto(List.of(
                RecentTracksDto.Item.of("track1", "Song X", "Artist Y")
        ));
        when(restTemplate.exchange(eq("http://api/recent?limit=20"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(RecentTracksDto.class)))
                .thenReturn(ResponseEntity.ok(recentDto));

        /* ---------- stub /features ---------- */
        AudioFeaturesDto featuresDto = new AudioFeaturesDto(List.of(
                AudioFeaturesDto.Feature.of("track1", 128, 0.80, 0.75)
        ));
        when(restTemplate.exchange(eq("http://api/features?ids=track1"),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(AudioFeaturesDto.class)))
                .thenReturn(ResponseEntity.ok(featuresDto));

        /* ---------- execute ---------- */
        List<Track> tracks = client.fetchRecentTracks("user123", 20);

        /* ---------- assertions ---------- */
        assertThat(tracks)
                .hasSize(1)
                .first()
                .satisfies(t -> {
                    assertThat(t.getId()).isEqualTo("track1");
                    assertThat(t.getAudio().getTempo()).isEqualTo(128);
                });

        /* ---------- headers capturados ---------- */
        @SuppressWarnings("unchecked")
        ArgumentCaptor<HttpEntity<Void>> capt = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplate, times(2))
                .exchange(anyString(), eq(HttpMethod.GET), capt.capture(), any(Class.class));

        HttpHeaders sentHeaders = capt.getAllValues().get(0).getHeaders();
        assertThat(sentHeaders.getFirst("X-User-Id")).isEqualTo("user123");
    }

    /* ---------------------------------------------------------------
       2) Edge-case: Spotify devuelve 0 pistas  → no se llama a /features
       --------------------------------------------------------------- */
    @Test
    @DisplayName("fetchRecentTracks – lista vacía → devuelve vacío y no pide features")
    void fetchRecentTracks_emptyList() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(HttpEntity.class), eq(RecentTracksDto.class)))
                .thenReturn(ResponseEntity.ok(new RecentTracksDto(List.of())));

        List<Track> tracks = client.fetchRecentTracks("u", 5);

        assertThat(tracks).isEmpty();

        // segundo endpoint nunca se llama
        verify(restTemplate, times(0))
                .exchange(contains("/features"), any(), any(), eq(AudioFeaturesDto.class));
    }
}