package com.hanif.portfolioapi.controller.artwork;

import com.hanif.portfolioapi.dto.artwork.ArtworkRequest;
import com.hanif.portfolioapi.dto.artwork.VisibilityRequest;
import com.hanif.portfolioapi.service.ArtworkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminArtworkController {

    private final ArtworkService artworkService;

    @PostMapping("/artworks/create")
    public void createArtwork(@Valid @RequestBody ArtworkRequest request) {
        artworkService.createArtwork(request);
    }

    @PostMapping("/artworks/{id}/update")
    public void updateArtwork(@PathVariable Long id, @Valid @RequestBody ArtworkRequest request) {
        artworkService.updateArtwork(id, request);
    }

    @PatchMapping("/artworks/{id}/visibility")
    public void visibility(@PathVariable Long id, @Valid @RequestBody VisibilityRequest request) {
        artworkService.visibility(id, request);
    }

    @DeleteMapping("/artworks/{id}/delete")
    public void deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);
    }
}
