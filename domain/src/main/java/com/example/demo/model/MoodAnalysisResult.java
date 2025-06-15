package com.example.demo.model;

import lombok.Value;

@Value
public class MoodAnalysisResult {
    Mood   mood;
    double confidence;  // 0-1 porcentaje de pistas que coinciden
}