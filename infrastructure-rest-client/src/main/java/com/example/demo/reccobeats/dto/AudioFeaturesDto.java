package com.example.demo.reccobeats.dto;

import lombok.Data;

/**
 * DTO para deserializar audio-features de Reccobeats
 */
@Data
public class AudioFeaturesDto {
    private double acousticness;
    private double danceability;
    private double energy;
    private double instrumentalness;
    private double liveness;
    private double loudness;
    private double speechiness;
    private double tempo;
    private double valence;
}