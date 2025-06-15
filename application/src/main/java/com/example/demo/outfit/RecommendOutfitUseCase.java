package com.example.demo.outfit;

import com.example.demo.model.*;
import com.example.demo.port.out.AudioFeaturesPort;
import com.example.demo.port.out.RecentTracksPort;
import com.example.demo.service.MoodAnalyzer;
import com.example.demo.service.OutfitRecommender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RecommendOutfitUseCase {


    private final RecentTracksPort   recentTracksPort;
    private final AudioFeaturesPort  audioPort;
    private final MoodAnalyzer       moodAnalyzer;
    private final OutfitRecommender  outfitRecommender;

    public OutfitSuggestion execute(String userId) {

        List<Track> tracks = recentTracksPort.fetchRecentTracks(userId, 20);

        if (tracks.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron tracks recientes");
        }

        List<String> spotifyIds = tracks.stream()
                .map(Track::getId)
                .toList();

        Map<String, AudioFeatures> featuresMap =
                audioPort.getAudioFeatures(spotifyIds);

        if (featuresMap.isEmpty()) {
            throw new IllegalArgumentException("No se pudieron obtener features de audio");
        }

        List<Track> enrichedTracks = tracks.stream()
                .filter(t -> featuresMap.containsKey(t.getId()))
                .map(t -> t.withAudioFeatures(featuresMap.get(t.getId())))
                .toList();

        MoodAnalysisResult analysis = moodAnalyzer.analyse(enrichedTracks);

        return outfitRecommender.recommend(analysis);
    }
}