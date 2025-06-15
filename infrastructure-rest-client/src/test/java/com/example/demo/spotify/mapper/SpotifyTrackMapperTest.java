package com.example.demo.spotify.mapper;

import com.example.demo.model.Track;
import com.example.demo.spotify.dto.AudioFeaturesDto;
import com.example.demo.spotify.dto.RecentTracksDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SpotifyTrackMapperTest {

    @Test
    void dtoIsMappedToDomain() {
        // Recent tracks DTO
        RecentTracksDto recent = new RecentTracksDto(List.of(
                RecentTracksDto.Item.of("track1", "Song A", "Artist A"),
                RecentTracksDto.Item.of("track2", "Song B", "Artist B")
        ));

        // Features DTO
        AudioFeaturesDto features = new AudioFeaturesDto(List.of(
                AudioFeaturesDto.Feature.of("track1", 128, 0.8, 0.75),
                AudioFeaturesDto.Feature.of("track2", 120, 0.6, 0.55)
        ));

        List<Track> domain = SpotifyTrackMapper.toDomain(recent, features);

        assertThat(domain).hasSize(2);
        assertThat(domain.get(0).getAudio().getTempo()).isEqualTo(128);
        assertThat(domain.get(1).getArtist()).isEqualTo("Artist B");
    }
}