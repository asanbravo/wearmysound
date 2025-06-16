package com.example.demo.spotify;

import com.example.demo.infrastructure.rest.api.OutfitsApi;
import com.example.demo.infrastructure.rest.api.model.OutfitSuggestionDto;
import com.example.demo.mapper.DomainToApiMapper;
import com.example.demo.model.OutfitSuggestion;
import com.example.demo.outfit.RecommendOutfitUseCase;
import com.example.demo.service.OutfitRecommender;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
public class OutfitRecommendationController implements OutfitsApi {

    private final RecommendOutfitUseCase useCase;
    private final DomainToApiMapper mapper;

    @Override
    public ResponseEntity<OutfitSuggestionDto> getOutfitRecommendation() {
        System.out.println("hellooooo");

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println("userId: " + userId);

        OutfitSuggestion outfit = useCase.execute(userId);
        return ResponseEntity.ok(mapper.toDto(outfit));
    }
}
