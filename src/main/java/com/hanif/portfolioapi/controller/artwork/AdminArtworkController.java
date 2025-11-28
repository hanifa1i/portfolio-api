package com.hanif.portfolioapi.controller.artwork;

import com.hanif.portfolioapi.dto.artwork.ArtworkRequest;
import com.hanif.portfolioapi.dto.artwork.VisibilityRequest;
import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.service.ArtworkService;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminArtworkController {

    private final ArtworkService artworkService;

    @PostMapping("/artworks/create")
    public ResponseEntity<ApiResponse> createArtwork(@Valid @RequestBody ArtworkRequest request) {
        Long id = artworkService.createArtwork(request);

        return ApiResponse.success(ResponseMessages.ARTWORK_CREATED, id);
    }

    @PostMapping("/artworks/{id}/update")
    public ResponseEntity<ApiResponse> updateArtwork(@PathVariable Long id, @Valid @RequestBody ArtworkRequest request) {
        artworkService.updateArtwork(id, request);

        return ApiResponse.success(ResponseMessages.ARTWORK_UPDATED, id);
    }

    @PatchMapping("/artworks/{id}/visibility")
    public ResponseEntity<ApiResponse> visibility(@PathVariable Long id, @Valid @RequestBody VisibilityRequest request) {
        artworkService.visibility(id, request);

        return ApiResponse.success(ResponseMessages.VISIBILITY_UPDATED, id);
    }

    @DeleteMapping("/artworks/{id}/delete")
    public ResponseEntity<ApiResponse> deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);

        return ApiResponse.success(ResponseMessages.ARTWORK_DELETED, id);
    }
}
