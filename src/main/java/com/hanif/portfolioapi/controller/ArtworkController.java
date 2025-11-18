package com.hanif.portfolioapi.controller;

import com.hanif.portfolioapi.dto.artwork.ArtworkResponse;
import com.hanif.portfolioapi.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artworks")
@RequiredArgsConstructor
public class ArtworkController {

    private final ArtworkService artworkService;

    @GetMapping
    public ResponseEntity<List<ArtworkResponse>> getAll() {
        return ResponseEntity.ok(artworkService.getAllArtworks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtworkResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(artworkService.getArtworkById(id));
    }

    @GetMapping("/tag/{name}")
    public ResponseEntity<List<ArtworkResponse>> getByTag(@PathVariable String name) {
        return ResponseEntity.ok(artworkService.getArtworksByTag(name));
    }



}
