package com.example.demo.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AudioFeaturesTest {

    @Test
    void equalsHashCodeAndToString() {
        AudioFeatures a = new AudioFeatures(0.5, 120.0, 0.8);
        AudioFeatures b = new AudioFeatures(0.5, 120.0, 0.8);
        AudioFeatures c = new AudioFeatures(0.4, 100.0, 0.3);

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
        assertThat(a).asString().contains("0.5", "120.0", "0.8");
        assertThat(a).isNotEqualTo(c);
    }

}