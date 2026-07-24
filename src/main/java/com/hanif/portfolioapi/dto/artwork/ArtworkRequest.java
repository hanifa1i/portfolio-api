package com.hanif.portfolioapi.dto.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.util.List;

@Data
public class ArtworkRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String title;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;

    private Boolean visible;

    @JsonProperty("image_urls")
    private List<String> imageUrls;

    @JsonProperty("tag_names")
    private List<
            @NotBlank(
                    message = ValidationMessages.REQUIRED
            ) String> tagNames;

    @JsonProperty("book_page")
    private Boolean bookPage;

    @JsonProperty("page_number")
    private Integer pageNumber;

    private String tool;
}
