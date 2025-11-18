package com.hanif.portfolioapi.dto.artwork;

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
    private LocalDateTime createAt;
    private Boolean visible;
    private List<String> imageUrls;
    private List<String> tagNames;
}
