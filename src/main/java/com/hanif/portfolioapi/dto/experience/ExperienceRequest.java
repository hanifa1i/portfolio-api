package com.hanif.portfolioapi.dto.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ExperienceRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    @JsonProperty("job_title")
    private String jobTitle;

    @NotBlank(message = ValidationMessages.REQUIRED)
    @JsonProperty("company_name")
    private String companyName;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String location;

    @Past(message = ValidationMessages.INVALID_PAST_DATE)
    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    private List<WorkProjectRequest> projects;

    private List<WorkActivityRequest> activities;

    private List<String> skills;
}
