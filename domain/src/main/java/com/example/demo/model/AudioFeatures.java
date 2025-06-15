package com.example.demo.model;

import lombok.Value;


import lombok.Value;

/**
 * Modelo de dominio para características de audio.
 */
@Value
public class AudioFeatures {
    double acousticness;
    double danceability;
    double energy;
    double instrumentalness;
    double liveness;
    double loudness;
    double speechiness;
    double tempo;
    double valence;
}
