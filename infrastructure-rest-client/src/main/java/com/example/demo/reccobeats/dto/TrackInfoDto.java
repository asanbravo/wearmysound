package com.example.demo.reccobeats.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Representa un track mapeado en Reccobeats
 */
@Data
public class TrackInfoDto {
    /** UUID interno de Reccobeats */
    private String id;

    /** Viene en la respuesta como la URL completa de Spotify */
    private String href;

    /**
     * Extrae el Spotify ID de la URL, p.ej.
     * https://open.spotify.com/track/5SWJyohWGUXsmpqkHVuozw
     */
    @JsonIgnore
    public String getSpotifyId() {
        if (href == null) return null;
        String[] parts = href.split("/");
        return parts[parts.length - 1];
    }
}
