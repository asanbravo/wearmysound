package com.example.demo.spotify.mapper;

import com.example.demo.model.AudioFeatures;
import com.example.demo.model.Track;
import com.example.demo.spotify.dto.AudioFeaturesDto;
import com.example.demo.spotify.dto.RecentTracksDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class SpotifyTrackMapper {

    public static List<Track> toDomain(RecentTracksDto recentDto,
                                       AudioFeaturesDto featuresDto) {

        Map<String, AudioFeaturesDto.Feature> featMap = featuresDto.toMap();

        return recentDto.getItems().stream()
                .map(item -> {
                    var t = item.getTrack();
                    var f = featMap.get(t.getId());

                    AudioFeatures audio = new AudioFeatures(
                            f.getTempo(),
                            f.getEnergy(),
                            f.getValence()
                    );

                    return new Track(
                            t.getId(),
                            t.getName(),
                            t.getArtists().get(0).getName(),
                            audio
                    );
                })
                .collect(Collectors.toList());
    }
}
