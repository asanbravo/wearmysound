package com.example.demo.outfit;

import com.example.demo.model.*;
import com.example.demo.port.out.AudioFeaturesPort;
import com.example.demo.port.out.RecentTracksPort;
import com.example.demo.service.MoodAnalyzer;
import com.example.demo.service.OutfitRecommender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecommendOutfitUseCaseTest {

    @Mock
    RecentTracksPort recentPort;
    @Mock
    AudioFeaturesPort audioPort;
    @Mock
    MoodAnalyzer moodAnalyzer;
    @Mock
    OutfitRecommender outfitRecommender;
    @InjectMocks
    RecommendOutfitUseCase useCase;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_happyPath() {
        Track t1 = new Track("id1","A","X", null);
        when(recentPort.fetchRecentTracks("u1", 20)).thenReturn(List.of(t1));
        when(audioPort.getAudioFeatures(List.of("id1")))
                .thenReturn(Map.of("id1", new AudioFeatures(0.8,120,0.6)));
        Track enriched = new Track("id1","A","X",
                new AudioFeatures(0.8,120,0.6));
        when(moodAnalyzer.analyse(List.of(enriched)))
                .thenReturn(new MoodAnalysisResult(Mood.ENERGETIC_HAPPY,1.0));
        OutfitSuggestion out = new OutfitSuggestion(Style.URBAN,
                new ColorPalette("p","s","a"), List.of());
        when(outfitRecommender.recommend(any())).thenReturn(out);

        OutfitSuggestion result = useCase.execute("u1");
        assertThat(result).isSameAs(out);
        verify(recentPort).fetchRecentTracks("u1", 20);
        verify(audioPort).getAudioFeatures(List.of("id1"));
        verify(moodAnalyzer).analyse(any());
        verify(outfitRecommender).recommend(any());
    }

    @Test
    void execute_emptyRecent_throws() {
        when(recentPort.fetchRecentTracks(any(), anyInt()))
                .thenReturn(List.of());
        assertThatThrownBy(() -> useCase.execute("u2"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void execute_noFeatures_throws() {
        Track t1 = new Track("id1","A","X", null);
        when(recentPort.fetchRecentTracks(any(), anyInt()))
                .thenReturn(List.of(t1));
        when(audioPort.getAudioFeatures(any()))
                .thenReturn(Map.of());
        assertThatThrownBy(() -> useCase.execute("u1"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}