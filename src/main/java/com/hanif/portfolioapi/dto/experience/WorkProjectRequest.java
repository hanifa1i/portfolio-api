package com.hanif.portfolioapi.dto.experience;

import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WorkProjectRequest {
    @NotBlank(message = ValidationMessages.REQUIRED)
    private String title;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;
}
