package com.example.demo.outfit;

import com.example.demo.model.AudioFeatures;
import com.example.demo.model.OutfitSuggestion;
import com.example.demo.model.Style;
import com.example.demo.model.Track;
import com.example.demo.port.out.RecentTracksPort;
import com.example.demo.port.out.SpotifyTokenPort;
import com.example.demo.service.MoodAnalyzer;
import com.example.demo.service.OutfitRecommender;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RecommendOutfitUseCaseTest {

    RecentTracksPort tracksPort = Mockito.mock(RecentTracksPort.class);

    MoodAnalyzer moodAnalyzer      = new MoodAnalyzer();
    OutfitRecommender outfitRecommender = new OutfitRecommender();

    RecommendOutfitUseCase useCase =
            new RecommendOutfitUseCase(tracksPort, moodAnalyzer, outfitRecommender);

    @Test
    void energeticTracksProduceUrbanSuggestion() {        AudioFeatures af = new AudioFeatures(130, 0.9, 0.8);
        List<Track> tracks = List.of(
                new Track("1","A","Art",af),
                new Track("2","B","Art",af)
        );
        Mockito.when(tracksPort.fetchRecentTracks("usr",20)).thenReturn(tracks);

        // Act
        OutfitSuggestion outfit = useCase.execute("usr");

        // Assert
        assertThat(outfit.getStyle()).isEqualTo(Style.URBAN);
    }
}