package com.hanif.portfolioapi.dto.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArtworkResponse {
    private Long id;
    private String title;
    private String description;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private Boolean visible;
    @JsonProperty("image_urls")
    private List<String> imageUrls;
    @JsonProperty("tag_names")
    private List<String> tagNames;
}
