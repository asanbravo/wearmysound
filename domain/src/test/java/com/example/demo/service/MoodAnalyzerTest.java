package com.example.demo.service;

import com.example.demo.model.AudioFeatures;
import com.example.demo.model.Mood;
import com.example.demo.model.MoodAnalysisResult;
import com.example.demo.model.Track;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoodAnalyzerTest {

    private final MoodAnalyzer analyzer = new MoodAnalyzer();

    @Test
    void energeticTracksYieldEnergeticHappyMood() {
        AudioFeatures af = new AudioFeatures(128, 0.85, 0.8);
        Track t1 = new Track("1", "Song A", "Artist", af);
        Track t2 = new Track("2", "Song B", "Artist", af);

        MoodAnalysisResult result = analyzer.analyse(List.of(t1, t2));

        assertThat(result.getMood()).isEqualTo(Mood.ENERGETIC_HAPPY);
        assertThat(result.getConfidence()).isEqualTo(1.0);
    }


    @Test
    void emptyTrackListThrowsException() {
        assertThatThrownBy(() -> analyzer.analyse(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Se requiere al menos un track");
    }

    @Test
    void mixTracksYieldChillMood() {
        // Solo 1 track energético de 3 (ratio = 0.33)
        AudioFeatures energetic = new AudioFeatures(128, 0.75, 0.7);
        AudioFeatures nonEnergetic = new AudioFeatures(128, 0.65, 0.4);
        Track t1 = new Track("1", "Energetic Song", "Artist", energetic);
        Track t2 = new Track("2", "Normal Song", "Artist", nonEnergetic);
        Track t3 = new Track("3", "Normal Song", "Artist", nonEnergetic);

        MoodAnalysisResult result = analyzer.analyse(List.of(t1, t2, t3));
        assertThat(result.getMood()).isEqualTo(Mood.CHILL);
        assertThat(result.getConfidence()).isEqualTo(1.0 / 3);
    }

    @Test
    void borderlineTracksYieldEnergeticHappyMood() {
        // 3 de 5 tracks son energéticos (ratio = 0.6)
        AudioFeatures energetic = new AudioFeatures(128, 0.8, 0.75);
        AudioFeatures nonEnergetic = new AudioFeatures(128, 0.65, 0.4);
        Track t1 = new Track("1", "Song 1", "Artist", energetic);
        Track t2 = new Track("2", "Song 2", "Artist", energetic);
        Track t3 = new Track("3", "Song 3", "Artist", energetic);
        Track t4 = new Track("4", "Song 4", "Artist", nonEnergetic);
        Track t5 = new Track("5", "Song 5", "Artist", nonEnergetic);

        MoodAnalysisResult result = analyzer.analyse(List.of(t1, t2, t3, t4, t5));
        assertThat(result.getMood()).isEqualTo(Mood.ENERGETIC_HAPPY);
        assertThat(result.getConfidence()).isEqualTo(0.6);
    }

}