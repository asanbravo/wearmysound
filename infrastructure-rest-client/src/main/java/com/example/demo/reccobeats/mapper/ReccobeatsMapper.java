package com.example.demo.reccobeats.mapper;

import com.example.demo.model.AudioFeatures;
import com.example.demo.reccobeats.dto.AudioFeaturesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReccobeatsMapper {

        @Mapping(target = "energy", source = "energy")
        @Mapping(target = "tempo", source = "tempo")
        @Mapping(target = "valence", source = "valence")
        AudioFeatures toAudioFeatures(AudioFeaturesDto dto);
}
