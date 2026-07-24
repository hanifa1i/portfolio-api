package com.hanif.portfolioapi.dto.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.dto.common.ImageResponse;
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
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private Boolean visible;
    @JsonProperty("image_urls")
    private List<ImageResponse> imageUrls;
    @JsonProperty("tag_names")
    private List<String> tagNames;
    @JsonProperty("book_page")
    private Boolean bookPage;
    @JsonProperty("page_number")
    private Integer pageNumber;
    private String tool;
}
