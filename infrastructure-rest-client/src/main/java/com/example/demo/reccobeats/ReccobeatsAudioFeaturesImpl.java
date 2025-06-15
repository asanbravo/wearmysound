package com.example.demo.reccobeats;

import com.example.demo.model.AudioFeatures;
import com.example.demo.port.out.AudioFeaturesPort;
import com.example.demo.reccobeats.dto.AudioFeaturesDto;
import com.example.demo.reccobeats.dto.TrackInfoDto;
import com.example.demo.reccobeats.dto.TrackSearchResponse;
import com.example.demo.reccobeats.mapper.ReccobeatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReccobeatsAudioFeaturesImpl implements AudioFeaturesPort {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;
    private final ReccobeatsMapper mapper;

    @Autowired
    public ReccobeatsAudioFeaturesImpl(
            RestTemplateBuilder builder,
            @Value("${reccobeats.api.base-url}") String baseUrl,
            @Value("${reccobeats.api.key:}") String apiKey,
            ReccobeatsMapper mapper
    ) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.mapper = mapper;
    }

    @Override
    public Map<String, AudioFeatures> getAudioFeatures(List<String> spotifyIds) {
        String uri = UriComponentsBuilder
                .fromUriString(baseUrl + "/track")
                .queryParam("ids", String.join(",", spotifyIds))
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        if (!apiKey.isEmpty()) {
            headers.set("X-API-Key", apiKey);
        }
        HttpEntity<?> req = new HttpEntity<>(headers);

        ResponseEntity<TrackSearchResponse> resp = restTemplate.exchange(
                uri, HttpMethod.GET, req, TrackSearchResponse.class);

        List<TrackInfoDto> found = resp.getBody().getContent();

        // Filtramos nulos y resolvemos posibles duplicados
        return found.stream()
                .filter(t -> t.getSpotifyId() != null)                         // descartamos nulls
                .collect(Collectors.toMap(
                        TrackInfoDto::getSpotifyId,
                        t -> fetchFeatures(t.getId()),
                        (existing, duplicate) -> existing                           // en caso de clave repetida, mantenemos la primera
                ));
    }

    private AudioFeatures fetchFeatures(String reccoId) {
        String url = baseUrl + "/track/" + reccoId + "/audio-features";
        HttpHeaders headers = new HttpHeaders();
        if (!apiKey.isEmpty()) {
            headers.set("X-API-Key", apiKey);
        }
        HttpEntity<?> req = new HttpEntity<>(headers);

        ResponseEntity<AudioFeaturesDto> resp = restTemplate.exchange(
                url,
                HttpMethod.GET,
                req,
                AudioFeaturesDto.class
        );
        return mapper.toAudioFeatures(resp.getBody());
    }
}