package com.example.demo.service;

import com.example.demo.model.AudioFeatures;
import com.example.demo.model.Mood;
import com.example.demo.model.MoodAnalysisResult;
import com.example.demo.model.Track;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.junit.jupiter.api.Assertions.*;
class MoodAnalyzerTest {

    private final MoodAnalyzer analyzer = new MoodAnalyzer();

    @Test
    void happyPath_energeticHappy() {
        // 3 de 5 pistas cumplen energy>0.7 y valence>0.5 → ratio=0.6
        List<Track> tracks = List.of(
                new Track("1","A","X", new AudioFeatures(0.8,100,0.6)),
                new Track("2","B","X", new AudioFeatures(0.9,110,0.7)),
                new Track("3","C","X", new AudioFeatures(0.75,120,0.8)),
                new Track("4","D","X", new AudioFeatures(0.5, 90,0.4)),
                new Track("5","E","X", new AudioFeatures(0.6, 95,0.3))
        );

        MoodAnalysisResult res = analyzer.analyse(tracks);
        assertThat(res.getMood()).isEqualTo(Mood.ENERGETIC_HAPPY);
        assertThat(res.getConfidence()).isCloseTo(0.6, within(1e-6));
    }

    @Test
    void happyPath_chill() {
        // ninguna cumple → ratio=0
        List<Track> tracks = List.of(
                new Track("1","A","X", new AudioFeatures(0.5,100,0.4)),
                new Track("2","B","X", new AudioFeatures(0.6,110,0.5))
        );

        MoodAnalysisResult res = analyzer.analyse(tracks);
        assertThat(res.getMood()).isEqualTo(Mood.CHILL);
        assertThat(res.getConfidence()).isEqualTo(0.0);
    }

    @Test
    void emptyList_throws() {
        assertThatThrownBy(() -> analyzer.analyse(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Se requiere al menos un track");
    }
  
}