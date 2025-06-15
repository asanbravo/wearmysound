package com.example.demo.service;

import com.example.demo.model.*;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MoodAnalyzer {

    public MoodAnalysisResult analyse(List<Track> tracks) {
        if (tracks.isEmpty())
            throw new IllegalArgumentException("Se requiere al menos un track");

        long energetic = tracks.stream()
                .filter(t -> t.getAudio().getEnergy()  > 0.7 &&
                        t.getAudio().getValence() > 0.5)
                .count();

        double ratio = energetic / (double) tracks.size();

        Mood mood = (ratio >= 0.6) ? Mood.ENERGETIC_HAPPY
                : Mood.CHILL;

        return new MoodAnalysisResult(mood, ratio);
    }
}
