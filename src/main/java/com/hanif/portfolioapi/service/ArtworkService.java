package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.artwork.ArtworkRequest;
import com.hanif.portfolioapi.dto.artwork.ArtworkResponse;
import com.hanif.portfolioapi.dto.artwork.VisibilityRequest;
import com.hanif.portfolioapi.model.Artwork;
import com.hanif.portfolioapi.model.ArtworkImage;
import com.hanif.portfolioapi.model.ArtworkTagLink;
import com.hanif.portfolioapi.model.Tag;
import com.hanif.portfolioapi.repository.ArtworkRepository;
import com.hanif.portfolioapi.repository.ArtworkTagLinkRepository;
import com.hanif.portfolioapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final TagRepository tagRepository;
    private final ArtworkTagLinkRepository artworkTagLinkRepository;

    public List<ArtworkResponse> getAllArtworks() {

        return artworkRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public ArtworkResponse getArtworkById(Long id) {
        return artworkRepository.findById(id)
                .map(this::toResponse).orElse(null);
    }

    public List<ArtworkResponse> getArtworksByTag(String tagName) {
        Long tagId = tagRepository.fingIdByName(tagName)
                .orElseThrow(() -> new RuntimeException("TAG DOESNT EXIST"));

        return artworkTagLinkRepository.findByTagId(tagId).stream()
                .map(this::toResponse).toList();
    }

    public void createArtwork(ArtworkRequest request) {

        Artwork artwork = mapToEntity(request);

        artworkRepository.save(artwork);
    }

    public void updateArtwork(Long id, ArtworkRequest request) {

        Artwork artwork = mapToEntity(request);

        artwork.setId(id);

        artworkRepository.save(artwork);
    }

    public void visibility(Long id, VisibilityRequest request) {

        Artwork artwork = artworkRepository.findById(id).orElseThrow(() -> new RuntimeException("ARTWORK DOESNT EXIST"));

        artwork.setVisible(request.isVisible());

        artworkRepository.save(artwork);
    }

    public void deleteArtwork(Long id) {
        artworkRepository.deleteById(id);
    }

    private Artwork mapToEntity(ArtworkRequest request) {

        Artwork artwork = Artwork.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .visible(true)
                .build();

        if (request.getImageUrls() != null) {
            for (String url : request.getImageUrls()) {
                ArtworkImage image = ArtworkImage.builder()
                        .artwork(artwork)
                        .imageUrl(url)
                        .build();

                artwork.addImage(image);
            }
        }

        if (request.getTagNames() != null) {
            for (String tagName : request.getTagNames()){
                Tag tag = tagRepository.fingByName(tagName)
                        .orElseThrow(() -> new RuntimeException("Tag invalid"));

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
                .updatedAt(artwork.getUpdatedAt())
                .visible(artwork.getVisible())
                .imageUrls(
                        artwork.getImages().stream()
                                .map(ArtworkImage::getImageUrl)
                                .toList())
                .tagNames(
                        artwork.getArtworkTagLinks().stream()
                                .map(link -> link.getTag().getName())
                                .toList())
                .build();
    }
}
