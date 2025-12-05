package com.hanif.portfolioapi.dto.artwork;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.validation.ValidationMessages;
import com.hanif.portfolioapi.validation.ValidationPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ArtworkRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String title;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;

    private Boolean visible;

    @JsonProperty("tag_names")
    private List<
            @NotBlank(
                    message = ValidationMessages.REQUIRED
            ) String> tagNames;
}
