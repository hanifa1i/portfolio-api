package com.hanif.portfolioapi.dto.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WorkActivityRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String activity;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String day;

    @NotBlank(message = ValidationMessages.REQUIRED)
    @JsonProperty("start_time")
    private String startTime;

    @NotBlank(message = ValidationMessages.REQUIRED)
    @JsonProperty("end_time")
    private String endTime;
}
