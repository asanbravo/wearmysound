package com.example.demo.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OutfitSuggestion {
    private final Style style;
    private final ColorPalette palette;
    private final List<Garment> garments;
}