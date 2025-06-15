package com.example.demo.reccobeats.dto;

import com.example.demo.reccobeats.ReccobeatsAudioFeaturesImpl;
import lombok.Data;

import java.util.List;

@Data
public class TrackSearchResponse {
    private List<TrackInfoDto> content;
}
