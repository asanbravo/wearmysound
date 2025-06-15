package com.example.demo.spotify;

import com.example.demo.infrastructure.rest.api.OutfitsApi;
import com.example.demo.infrastructure.rest.api.model.OutfitSuggestionDto;
import com.example.demo.model.ColorPalette;
import com.example.demo.model.Garment;
import com.example.demo.model.OutfitSuggestion;
import com.example.demo.model.Style;
import com.example.demo.outfit.RecommendOutfitUseCase;
import com.example.demo.mapper.DomainToApiMapper;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class OutfitRecommendationControllerTest {


}