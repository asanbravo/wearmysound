package com.example.demo.spotify.mapper;

import com.example.demo.model.SpotifyToken;
import com.example.demo.spotify.dto.SpotifyTokenApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpotifyTokenMapper {

    @Mapping(source = "expiresIn", target = "expiresIn")
    @Mapping(target = "obtainedAtEpochSeconds",
            expression = "java(java.time.Instant.now().getEpochSecond())")
    SpotifyToken toDomain(SpotifyTokenApiResponse dto);
}