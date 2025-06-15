package com.example.demo.model;

import lombok.Value;

@Value
public class AudioFeatures {

    double tempo;   // BPM   [>0]
    double energy;  // [0-1]
    double valence; // [0-1]

    public AudioFeatures(double tempo, double energy, double valence) {
        if (tempo <= 0 || energy < 0 || energy > 1 || valence < 0 || valence > 1)
            throw new IllegalArgumentException("Valores fuera de rango");
        this.tempo   = tempo;
        this.energy  = energy;
        this.valence = valence;
    }
}