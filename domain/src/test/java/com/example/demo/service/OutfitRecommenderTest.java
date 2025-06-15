package com.example.demo.service;

import com.example.demo.model.Mood;
import com.example.demo.model.MoodAnalysisResult;
import com.example.demo.model.OutfitSuggestion;
import com.example.demo.model.Style;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OutfitRecommenderTest {

    private final OutfitRecommender recommender = new OutfitRecommender();

    @Test
    @DisplayName("Energetic Happy -> Urban outfit")
    void energeticHappyMoodYieldsUrbanOutfit() {
        MoodAnalysisResult result = new MoodAnalysisResult(Mood.ENERGETIC_HAPPY, 0.8);
        OutfitSuggestion outfit = recommender.recommend(result);

        assertThat(outfit.getStyle()).isEqualTo(Style.URBAN);
        assertThat(outfit.getPalette().getPrimary()).isEqualTo("#FF5722");
        assertThat(outfit.getGarments()).hasSize(2);
    }

    @Test
    @DisplayName("Chill -> Casual outfit")
    void chillMoodYieldsCasualOutfit() {
        MoodAnalysisResult result = new MoodAnalysisResult(Mood.CHILL, 0.9);
        OutfitSuggestion outfit = recommender.recommend(result);

        assertThat(outfit.getStyle()).isEqualTo(Style.CASUAL);
        // Se verifica que se usa la paleta pastel para chill
        assertThat(outfit.getPalette().getPrimary()).isEqualTo("#B0BEC5");
        assertThat(outfit.getGarments()).hasSize(2);
    }

    @Test
    @DisplayName("Caso por defecto -> Elegant outfit")
    void otherMoodYieldsElegantOutfit() {
        // Para probar la rama default, se utiliza un valor de mood que no sea ENERGETIC_HAPPY ni CHILL.
        // Se asume que la enumeración Mood tiene otro valor, por ejemplo, NEUTRAL.
        MoodAnalysisResult result = new MoodAnalysisResult(Mood.NEUTRAL, 0.7);
        OutfitSuggestion outfit = recommender.recommend(result);

        assertThat(outfit.getStyle()).isEqualTo(Style.ELEGANT);
        assertThat(outfit.getPalette().getPrimary()).isEqualTo("#263238");
        assertThat(outfit.getGarments()).hasSize(2);
    }
}