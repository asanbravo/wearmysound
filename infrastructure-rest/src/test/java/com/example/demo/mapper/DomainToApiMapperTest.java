// File: infrastructure-rest/src/test/java/com/example/demo/mapper/DomainToApiMapperTest.java
package com.example.demo.mapper;

import com.example.demo.infrastructure.rest.api.model.ColorPaletteDto;
import com.example.demo.infrastructure.rest.api.model.GarmentDto;
import com.example.demo.infrastructure.rest.api.model.OutfitSuggestionDto;
import com.example.demo.infrastructure.rest.api.model.StyleDto;
import com.example.demo.model.ColorPalette;
import com.example.demo.model.Garment;
import com.example.demo.model.OutfitSuggestion;
import com.example.demo.model.Style;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class DomainToApiMapperTest {

    private final DomainToApiMapper mapper = Mappers.getMapper(DomainToApiMapper.class);

    @Test
    void mapsOutfitSuggestion() {
        OutfitSuggestion dom = new OutfitSuggestion(
                Style.CASUAL,
                new ColorPalette("#111111","#222222","#333333"),
                List.of(new Garment("jacket","Desc", "http://shop"))
        );
        OutfitSuggestionDto dto = mapper.toDto(dom);

        assertThat(dto.getStyle()).isEqualTo(StyleDto.CASUAL);
        assertThat(dto.getPalette().getPrimary()).isEqualTo("#111111");
        assertThat(dto.getGarments()).hasSize(1);
        GarmentDto g = dto.getGarments().get(0);
        assertThat(g.getCategory()).isEqualTo("jacket");
        assertThat(g.getShopLink()).isEqualTo(URI.create("http://shop"));
    }

    @Test
    void mapsNullShopLinkToNullUri() {
        Garment gar = new Garment("s", "d", null);
        GarmentDto dto = mapper.toDto(gar);
        assertThat(dto.getShopLink()).isNull();
    }
}