package com.example.demo.spotify.dto;

import com.example.demo.model.Track;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecentTracksDto {

    @JsonProperty("items")
    private List<Item> items;

    public static RecentTracksDto of(Item... items) {
        return new RecentTracksDto(List.of(items));
    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        @JsonProperty("track") private TrackDto track;
        public static Item of(String id, String name, String artist) {
            return new Item(new TrackDto(id, name, List.of(new ArtistDto(artist))));
        }
    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackDto {
        private String id;
        private String name;
        private List<ArtistDto> artists;

    }

    @Getter
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArtistDto {
        private String name;
    }

    /** Devuelve los IDs separados por coma para usarlos en /audio-features */
    public String idsCsv() {
        return items.stream()
                .map(i -> i.getTrack().getId())
                .collect(Collectors.joining(","));
    }
}