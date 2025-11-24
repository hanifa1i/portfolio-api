package com.hanif.portfolioapi.dto.artwork;

import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotNull;

public class VisibilityRequest {

    @NotNull(message = ValidationMessages.REQUIRED)
    private Boolean visible;

    public boolean isVisible() {
        return visible;
    }
}
