package com.example.demo.model;

import lombok.Value;


import lombok.Value;

/**
 * Modelo de dominio para características de audio.
 */
@Value
public class AudioFeatures {
    double energy;
    double tempo;
    double valence;
}
