package com.example.demo.spotify;

import com.example.demo.model.Track;
import com.example.demo.spotify.dto.RecentTracksDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class SpotifyTrackClientImplTest {

    private MockRestServiceServer server;
    private SpotifyTrackClientImpl client;
    private RestTemplate rest;

    @BeforeEach
    void setup() {
        rest = new RestTemplate();
        server = MockRestServiceServer.createServer(rest);
        client = new SpotifyTrackClientImpl(rest);
        // inyecta la URL de test
        ReflectionTestUtils.setField(client, "recentTracksUrl", "http://api/spotify/recent?limit={limit}");
    }

    @Test
    void fetchRecent_success() {
        String json = """
            {"items":[
               {"track":{"id":"t1","name":"Song1","artists":[{"name":"A1"}]}},
               {"track":{"id":"t2","name":"Song2","artists":[{"name":"A2"}]}}
            ]}
            """;
        server.expect(requestTo("http://api/spotify/recent?limit=5"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));

        List<Track> tracks = client.fetchRecentTracks("u1", 5);
        assertThat(tracks).hasSize(2);
        assertThat(tracks.get(0).getId()).isEqualTo("t1");
        assertThat(tracks.get(0).getAudio()).isNull();
        server.verify();
    }

    @Test
    void fetchRecent_emptyOrNullBody_returnsEmptyList() {
        server.expect(requestTo("http://api/spotify/recent?limit=3"))
                .andRespond(withSuccess("{\"items\":[]}", MediaType.APPLICATION_JSON));
        assertThat(client.fetchRecentTracks("uX", 3)).isEmpty();

        server.reset();
        server.expect(requestTo("http://api/spotify/recent?limit=3"))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));
        assertThat(client.fetchRecentTracks("uX", 3)).isEmpty();
    }

    @Test
    void fetchRecent_errorPropagates() {
        server.expect(requestTo("http://api/spotify/recent?limit=2"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThatThrownBy(() -> client.fetchRecentTracks("u1", 2))
                .isInstanceOf(Exception.class);
    }
  
}