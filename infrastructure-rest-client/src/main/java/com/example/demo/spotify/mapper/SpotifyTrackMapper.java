package com.example.demo.spotify.mapper;

import com.example.demo.model.Track;
import com.example.demo.spotify.dto.RecentTracksDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class SpotifyTrackMapper {

    /**
     * Mapea el DTO de Spotify a la lista de Tracks del dominio,
     * dejando audioFeatures a null (se inyectará desde Reccobeats).
     */
    public static List<Track> toDomain(RecentTracksDto recentDto) {
        return recentDto.getItems().stream()
                .map(item -> {
                    var t = item.getTrack();
                    return new Track(
                            t.getId(),
                            t.getName(),
                            t.getArtists().get(0).getName(),
                            null  // AudioFeatures se añade posteriormente
                    );
                })
                .collect(Collectors.toList());
    }
}