// File: infrastructure-rest-client/src/test/java/com/example/demo/spotify/mapper/SpotifyTrackMapperTest.java
package com.example.demo.spotify.mapper;

import com.example.demo.model.Track;
import com.example.demo.spotify.dto.RecentTracksDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
class SpotifyTrackMapperTest {
    @Test
    void toDomain_mapsCorrectly() {
        RecentTracksDto.ArtistDto art = new RecentTracksDto.ArtistDto("X");
        RecentTracksDto.TrackDto t = new RecentTracksDto.TrackDto("i1", "N", List.of(art));
        RecentTracksDto.ItemDto item = new RecentTracksDto.ItemDto(t);
        RecentTracksDto dto = new RecentTracksDto(List.of(item));

        List<Track> list = SpotifyTrackMapper.toDomain(dto);
        assertThat(list)
                .extracting(Track::getId, Track::getTitle, Track::getArtist)
                .containsExactly(tuple("i1", "N", "X"));
        assertThat(list.get(0).getAudio()).isNull();
    }
}