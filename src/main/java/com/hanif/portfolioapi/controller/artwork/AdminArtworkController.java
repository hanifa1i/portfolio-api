package com.hanif.portfolioapi.controller.artwork;

import com.hanif.portfolioapi.dto.artwork.ArtworkRequest;
import com.hanif.portfolioapi.dto.artwork.VisibilityRequest;
import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.dto.common.UpdateImagesRequest;
import com.hanif.portfolioapi.service.ArtworkService;
import com.hanif.portfolioapi.service.S3Service;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/artworks")
@RequiredArgsConstructor
public class AdminArtworkController {

    private final ArtworkService  artworkService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createArtwork(@Valid @RequestBody ArtworkRequest request) {
        Long id = artworkService.createArtwork(request);

        return ApiResponse.success(ResponseMessages.ARTWORK_CREATED, id);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateArtwork(@PathVariable Long id, @Valid @RequestBody ArtworkRequest request) {
        artworkService.updateArtwork(id, request);

        return ApiResponse.success(ResponseMessages.ARTWORK_UPDATED, id);
    }

    @PatchMapping("/{id}/visibility")
    public ResponseEntity<ApiResponse> visibility(@PathVariable Long id, @Valid @RequestBody VisibilityRequest request) {
        artworkService.visibility(id, request);

        return ApiResponse.success(ResponseMessages.VISIBILITY_UPDATED, id);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteArtwork(@PathVariable Long id) {
        artworkService.deleteArtwork(id);

        return ApiResponse.success(ResponseMessages.ARTWORK_DELETED, id);
    }

    @PostMapping("/{id}/image/upload")
    public ResponseEntity<ApiResponse> uploadArtworkImage(@PathVariable Long id,
                                                     @RequestParam("image") MultipartFile image) throws IOException {

        String imageUrl = artworkService.uploadArtworkImage(id, image);

        return ApiResponse.success(imageUrl, null);
    }
    @PostMapping("/{id}/image/update")
    public ResponseEntity<ApiResponse> updateArtworkImage(@PathVariable Long id, @RequestBody UpdateImagesRequest request){

        List<String> imageUrls = artworkService.updateArtworkImages(id, request);

        return ApiResponse.success(imageUrls.toString(), id);
    }

    @DeleteMapping("/{artworkId}/image/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteArtworkImage(@PathVariable Long artworkId, @PathVariable Long imageId) {

        String key = artworkService.deleteArtworkImage(artworkId, imageId);

        return ApiResponse.success("Image deleted: " + key, null);
    }

}
