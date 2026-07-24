package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.artwork.ArtworkRequest;
import com.hanif.portfolioapi.dto.artwork.ArtworkResponse;
import com.hanif.portfolioapi.dto.artwork.VisibilityRequest;
import com.hanif.portfolioapi.dto.common.ImageResponse;
import com.hanif.portfolioapi.dto.common.UpdateImagesRequest;
import com.hanif.portfolioapi.enums.ActionType;
import com.hanif.portfolioapi.enums.EntityType;
import com.hanif.portfolioapi.exceptions.NotFoundException;
import com.hanif.portfolioapi.model.Artwork;
import com.hanif.portfolioapi.model.ArtworkImage;
import com.hanif.portfolioapi.model.ArtworkTagLink;
import com.hanif.portfolioapi.model.Tag;
import com.hanif.portfolioapi.repository.ArtworkRepository;
import com.hanif.portfolioapi.repository.ArtworkTagLinkRepository;
import com.hanif.portfolioapi.repository.TagRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final TagRepository tagRepository;
    private final ArtworkTagLinkRepository artworkTagLinkRepository;
    private final S3Service s3Service;
    private final ActivityLogService activityLogService;

    public List<ArtworkResponse> getAllArtworks() {

        return artworkRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public ArtworkResponse getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.ARTWORK_NOT_FOUND, id)));
    }

    public List<ArtworkResponse> getRecent(int amount) {
        return artworkRepository.findAllByBookPageFalseOrderByCreatedAtDesc(PageRequest.of(0, amount))
                .stream()
                .map(this::toResponse)
                .toList();
    }
    public List<ArtworkResponse> getSketchbookArt() {
        return artworkRepository.findAll().stream()
                .filter((artwork -> artwork.getBookPage() == true))
                .map(this::toResponse).toList();
    }
    public List<ArtworkResponse> getStandaloneArtworks() {
        return artworkRepository.findAll().stream()
                .filter((artwork -> artwork.getBookPage() == false))
                .map(this::toResponse).toList();
    }

    public List<ArtworkResponse> getArtworksByTag(String tagName) {
        Long tagId = tagRepository.fingIdByName(tagName)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.TAG_NOT_FOUND, tagName)));

        return artworkTagLinkRepository.findByTagId(tagId).stream()
                .map(this::toResponse).toList();
    }

    public Long createArtwork(ArtworkRequest request) {
        Artwork artwork = mapToEntity(request, 0);
        artworkRepository.save(artwork);
        if (artwork.getBookPage() == true) {
            activityLogService.addActivityLog(artwork.getId(), EntityType.SKETCHBOOK_PAGE, ActionType.CREATE, artwork.getTitle() + " - Pg " + artwork.getPageNumber());
        }
        else {
            activityLogService.addActivityLog(artwork.getId(), EntityType.ARTWORK, ActionType.CREATE, null);
        }

        return artwork.getId();
    }

    public void updateArtwork(Long id, ArtworkRequest request) {

        artworkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.ARTWORK_NOT_FOUND, id)));

        Artwork artwork = mapToEntity(request, id);

        artwork.setId(id);

        artworkRepository.save(artwork);
        if (artwork.getBookPage() == true) {
            activityLogService.addActivityLog(artwork.getId(), EntityType.SKETCHBOOK_PAGE, ActionType.UPDATE, artwork.getTitle() + " - Pg " + artwork.getPageNumber());
        }
        else {
            activityLogService.addActivityLog(artwork.getId(), EntityType.ARTWORK, ActionType.UPDATE, null);
        }
    }

    public String uploadArtworkImage(Long id, MultipartFile file) throws IOException {

        String url = s3Service.uploadFile("artwork", id, file);

        Artwork updatedArtwork = updateArtworkUrl(id, url);

        artworkRepository.save(updatedArtwork);

        return url;
    }
    public List<String> updateArtworkImages(Long id, UpdateImagesRequest request) {

        for (String url : request.getImages()) {
            Artwork updatedArtwork = updateArtworkUrl(id, url);
            artworkRepository.save(updatedArtwork);
        }

        return request.getImages();
    }

    public void deleteArtwork(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.ARTWORK_NOT_FOUND, id)));

        artwork.getImages().stream()
                .map(ArtworkImage::getImageUrl)
                .map(this::extractKey)
                .forEach(s3Service::deleteFile);

        artworkRepository.deleteById(id);
        if (artwork.getBookPage() == true) {
            activityLogService.addActivityLog(artwork.getId(), EntityType.SKETCHBOOK_PAGE, ActionType.DELETE, artwork.getTitle() + " - Pg " + artwork.getPageNumber());
        }
        else {
            activityLogService.addActivityLog(artwork.getId(), EntityType.ARTWORK, ActionType.DELETE, null);
        }
    }

    public String deleteArtworkImage(Long artworkId, Long imageId) {

        Artwork artwork = artworkRepository.findById(artworkId)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.ARTWORK_NOT_FOUND, artworkId)));

        ArtworkImage image = getArtworkImage(artwork, imageId);

        String key = extractKey(image.getImageUrl());

        s3Service.deleteFile(key);

        artwork.getImages().remove(image);

        artworkRepository.save(artwork);

        return key;

    }

    private ArtworkImage getArtworkImage(Artwork artwork, Long imageId) {
        return artwork.getImages()
                .stream()
                .filter(img -> img.getId().equals(imageId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        String.format(ResponseMessages.IMAGE_NOT_FOUND, imageId)
                ));
    }
    private String extractKey(String imageUrl) {
        int index = imageUrl.indexOf(".com/") + 5;

        return imageUrl.substring(index);
    }

    private Artwork updateArtworkUrl(Long id, String imageUrl) {

        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.ARTWORK_NOT_FOUND, id)));


        ArtworkImage artworkImage = ArtworkImage.builder()
                .artwork(artwork)
                .imageUrl(imageUrl)
                .build();

        List<ArtworkImage> artworkImages = artwork.getImages();

        artworkImages.add(artworkImage);

        return artwork;
    }

    public void visibility(Long id, VisibilityRequest request) {

        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.ARTWORK_NOT_FOUND, id)));

        artwork.setVisible(request.isVisible());

        artworkRepository.save(artwork);
    }

    private Artwork mapToEntity(ArtworkRequest request, long id) {

        Artwork artwork = Artwork.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .bookPage(request.getBookPage())
                .pageNumber(request.getPageNumber())
                .tool(request.getTool())
                .visible(true)
                .build();

        if (id != 0 && request.getImageUrls() != null) {
            for (String imageUrl : request.getImageUrls()) {
                ArtworkImage artworkImage = ArtworkImage.builder()
                        .id(id)
                        .artwork(artwork)
                        .imageUrl(imageUrl)
                        .build();
                artwork.addImage(artworkImage);
            }
        }
        if (request.getTagNames() != null) {
            for (String tagName : request.getTagNames()){
                Tag tag = tagRepository.fingByName(tagName)
                        .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.TAG_NOT_FOUND, tagName)));

                ArtworkTagLink artworkTagLink = ArtworkTagLink.builder()
                        .artwork(artwork)
                        .tag(tag)
                        .build();

                artwork.addTagLink(artworkTagLink);
            }
        }
        return artwork;
    }

    private ArtworkResponse toResponse(Artwork artwork) {
        return ArtworkResponse.builder()
                .id(artwork.getId())
                .title(artwork.getTitle())
                .description(artwork.getDescription())
                .createdAt(artwork.getCreatedAt())
                .updatedAt(artwork.getUpdatedAt())
                .visible(artwork.getVisible())
                .imageUrls(
                        artwork.getImages().stream()
                                .map(image -> ImageResponse.builder()
                                        .id(image.getId())
                                        .imageUrl(image.getImageUrl())
                                        .build())
                                .toList())
                .tagNames(
                        artwork.getArtworkTagLinks().stream()
                                .map(link -> link.getTag().getName())
                                .toList())
                .bookPage(artwork.getBookPage())
                .pageNumber(artwork.getPageNumber())
                .tool(artwork.getTool())
                .build();
    }
}
