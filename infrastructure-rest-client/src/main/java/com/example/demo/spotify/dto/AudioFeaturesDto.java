package com.example.demo.spotify.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioFeaturesDto {

    @JsonProperty("audio_features")
    private List<Feature> audioFeatures;

    public static AudioFeaturesDto of(Feature... features) {
        return new AudioFeaturesDto(List.of(features));
    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {
        private String id;
        private double tempo;
        private double energy;
        private double valence;

        public static Feature of(String id, int tempo, double energy, double valence) {
            return new Feature(id, tempo, energy, valence);
        }
    }

    /** Mapa id → Feature para lookup rápido */
    public Map<String, Feature> toMap() {
        return audioFeatures.stream()
                .collect(Collectors.toMap(Feature::getId, f -> f));
    }
}