package com.example.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Track {

    @EqualsAndHashCode.Include
    private final String id;           // Spotify track ID

    private final String title;
    private final String artist;

    private final AudioFeatures audio; // Value-Object
}