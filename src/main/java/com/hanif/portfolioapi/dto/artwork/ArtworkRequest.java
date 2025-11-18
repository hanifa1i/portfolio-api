package com.hanif.portfolioapi.dto.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ArtworkRequest {
    private String title;
    private String description;
    private Boolean visible;

    @JsonProperty("image_urls")
    private List<String> imageUrls;

    @JsonProperty("tag_names")
    private List<String> tagNames;
}
