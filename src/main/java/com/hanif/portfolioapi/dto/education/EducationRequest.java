package com.hanif.portfolioapi.dto.education;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EducationRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String qualification;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String institution;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String level;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String grade;

    @Past(message = ValidationMessages.INVALID_PAST_DATE)
    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private String description;

    @JsonProperty("certificates")
    private List<String> certificates;
}
