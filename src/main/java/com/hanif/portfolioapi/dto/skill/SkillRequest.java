package com.hanif.portfolioapi.dto.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.enums.SkillType;
import com.hanif.portfolioapi.validation.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
public class SkillRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String name;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;

    @NotBlank(message = ValidationMessages.REQUIRED)
    @JsonProperty("skill_type")
    private SkillType skillType;

    @NotBlank(message = ValidationMessages.REQUIRED)
    @JsonProperty("experience_locations")
    private Set<ExperienceLocation> experienceLocations;

    private Boolean visible;
}
