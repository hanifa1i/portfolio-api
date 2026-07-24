package com.hanif.portfolioapi.dto.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.enums.SkillType;
import com.hanif.portfolioapi.model.SkillExample;
import com.hanif.portfolioapi.validation.ValidationMessages;
import com.hanif.portfolioapi.validation.enumvalidation.ValidEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SkillRequest {

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String name;

    @NotBlank(message = ValidationMessages.REQUIRED)
    private String description;

    @ValidEnum(enumClass = SkillType.class, message = ValidationMessages.INVALID_ENUM)
    @JsonProperty("skill_type")
    private String skillType;

    @NotNull(message = ValidationMessages.NULL)
    @JsonProperty("experience_locations")
    private Set<
            @ValidEnum(enumClass = ExperienceLocation.class, message = ValidationMessages.INVALID_ENUM) String
            > experienceLocations;

    private Boolean visible;

    private List<SkillExample> examples;
}
