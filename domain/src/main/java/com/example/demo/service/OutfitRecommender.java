package com.example.demo.service;

import com.example.demo.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutfitRecommender {

    public OutfitSuggestion recommend(MoodAnalysisResult analysis) {

        switch (analysis.getMood()) {
            case ENERGETIC_HAPPY -> {
                return new OutfitSuggestion(
                        Style.URBAN,
                        new ColorPalette("#FF5722", "#FFC107", "#FFFFFF"), // vibrante
                        List.of(
                                new Garment("jacket", "Oversized bomber", null),
                                new Garment("sneakers", "High-top sneakers", null)
                        ));
            }
            case CHILL -> {
                return new OutfitSuggestion(
                        Style.CASUAL,
                        new ColorPalette("#B0BEC5", "#ECEFF1", "#607D8B"), // pastel
                        List.of(
                                new Garment("hoodie", "Soft cotton hoodie", null),
                                new Garment("jeans", "Relaxed-fit jeans", null)
                        ));
            }
            default -> {
                return new OutfitSuggestion(
                        Style.ELEGANT,
                        new ColorPalette("#263238", "#455A64", "#CFD8DC"), // neutro
                        List.of(
                                new Garment("blazer", "Tailored blazer", null),
                                new Garment("shoes", "Leather loafers", null)
                        ));
            }
        }
    }
}