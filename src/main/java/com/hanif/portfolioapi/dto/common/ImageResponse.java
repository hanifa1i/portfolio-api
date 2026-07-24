package com.hanif.portfolioapi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private Long id;

    @JsonProperty("image_url")
    private String imageUrl;
}
