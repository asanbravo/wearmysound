package com.example.demo.outfit;

import com.example.demo.model.*;
import com.example.demo.port.out.RecentTracksPort;
import com.example.demo.service.MoodAnalyzer;
import com.example.demo.service.OutfitRecommender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendOutfitUseCase {

    private final RecentTracksPort   recentTracksPort;
    private final MoodAnalyzer       moodAnalyzer;
    private final OutfitRecommender  outfitRecommender;

    public OutfitSuggestion execute(String userId) {

        // 1. Pedir pistas recientes (el adaptador REST usará el token)
        List<Track> tracks = recentTracksPort.fetchRecentTracks(userId, 20);

        // 2. Analizar mood
        MoodAnalysisResult analysis = moodAnalyzer.analyse(tracks);

        // 3. Recomendar outfit
        return outfitRecommender.recommend(analysis);
    }
}