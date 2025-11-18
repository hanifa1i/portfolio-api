package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.artwork.ArtworkResponse;
import com.hanif.portfolioapi.model.Artwork;
import com.hanif.portfolioapi.model.ArtworkImage;
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



    private ArtworkResponse toResponse(Artwork artwork) {
        return ArtworkResponse.builder()
                .id(artwork.getId())
                .title(artwork.getTitle())
                .description(artwork.getDescription())
                .createAt(artwork.getCreateAt())
                .visible(artwork.getVisible())
                .imageUrls(
                        artwork.getImages().stream()
                                .map(ArtworkImage::getImageUrl)
                                .toList())
                .tagNames(
                        artwork.getArtworkTagLinks().stream()
                                .map(link -> link.getTag().getName())
                                .toList()
                ).build();
    }
}
