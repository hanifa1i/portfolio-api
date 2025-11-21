package com.hanif.portfolioapi.dto.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.enums.SkillType;
import lombok.Data;

import java.util.Set;

@Data
public class SkillRequest {
    private String name;
    private String description;
    @JsonProperty("skill_type")
    private SkillType skillType;
    @JsonProperty("experience_locations")
    private Set<ExperienceLocation> experienceLocations;
    private Boolean visible;
}
