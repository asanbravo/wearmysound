package com.example.demo.reccobeats.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Representa un track mapeado en Reccobeats
 */
@Data
public class TrackInfoDto {
    /**
     * ID interno de Reccobeats (UUID)
     */
    private String id;

    /**
     * ID original de Spotify utilizado para la búsqueda
     */
    @JsonProperty("spotifyId")
    private String spotifyId;
}
