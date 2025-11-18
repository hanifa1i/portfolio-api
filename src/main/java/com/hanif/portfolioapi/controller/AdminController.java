package com.hanif.portfolioapi.controller;

import com.hanif.portfolioapi.dto.artwork.ArtworkRequest;
import com.hanif.portfolioapi.dto.artwork.VisibilityRequest;
import com.hanif.portfolioapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/artworks/create")
    public void createArtwork(@RequestBody ArtworkRequest request) {
        adminService.createArtwork(request);
    }

    @PostMapping("/artworks/{id}/update")
    public void updateArtwork(@PathVariable Long id, @RequestBody ArtworkRequest request) {
        adminService.updateArtwork(id, request);
    }

    @PatchMapping("/artworks/{id}/visibility")
    public void visibility(@PathVariable Long id, @RequestBody VisibilityRequest request) {
        adminService.visibility(id, request);
    }

    @DeleteMapping("/artworks/{id}/delete")
    public void deleteArtwork(@PathVariable Long id) {
        adminService.deleteArtwork(id);
    }


}
