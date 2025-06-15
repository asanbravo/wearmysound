package com.example.demo.reccobeats.mapper;

import com.example.demo.model.AudioFeatures;
import com.example.demo.reccobeats.dto.AudioFeaturesDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReccobeatsMapper {
        AudioFeatures toAudioFeatures(AudioFeaturesDto dto);
}
