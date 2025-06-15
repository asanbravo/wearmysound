package com.example.demo.mapper;

import com.example.demo.infrastructure.rest.api.model.ColorPaletteDto;
import com.example.demo.infrastructure.rest.api.model.GarmentDto;
import com.example.demo.infrastructure.rest.api.model.OutfitSuggestionDto;
import com.example.demo.infrastructure.rest.api.model.StyleDto;
import com.example.demo.model.ColorPalette;
import com.example.demo.model.Garment;
import com.example.demo.model.Style;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.net.URI;

@Mapper(componentModel = "spring")
public interface DomainToApiMapper {

    /* ===== Outfit ===== */

    @Mapping(source = "style",    target = "style")
    @Mapping(source = "palette",  target = "palette")
    @Mapping(source = "garments", target = "garments")
    OutfitSuggestionDto toDto(com.example.demo.model.OutfitSuggestion domain);

    /* ===== Value Objects ===== */

    ColorPaletteDto toDto(ColorPalette palette);

    GarmentDto toDto(Garment garment);

    /* ===== Enum ===== */

    default StyleDto toDto(Style style) {
        return StyleDto.valueOf(style.name());
    }

    default URI map(String value) {
        return value == null ? null : URI.create(value);
    }
}