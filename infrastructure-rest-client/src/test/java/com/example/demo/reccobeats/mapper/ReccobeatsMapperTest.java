package com.example.demo.reccobeats.mapper;

import com.example.demo.model.AudioFeatures;
import com.example.demo.reccobeats.dto.AudioFeaturesDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class ReccobeatsMapperTest {

    private final ReccobeatsMapper mapper = Mappers.getMapper(ReccobeatsMapper.class);

    @Test
    void toAudioFeatures_mapsAllFields() {
        AudioFeaturesDto dto = new AudioFeaturesDto();
        dto.setEnergy(0.4);
        dto.setTempo(90.0);
        dto.setValence(0.7);

        AudioFeatures af = mapper.toAudioFeatures(dto);
        assertThat(af.getEnergy()).isEqualTo(0.4);
        assertThat(af.getTempo()).isEqualTo(90.0);
        assertThat(af.getValence()).isEqualTo(0.7);
    }
}