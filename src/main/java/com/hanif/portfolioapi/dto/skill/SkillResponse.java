package com.hanif.portfolioapi.dto.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.enums.SkillType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SkillResponse {
    private Long id;
    private String name;
    private String description;
    @JsonProperty("skill_type")
    private SkillType skillType;
    @JsonProperty("experience_locations")
    private List<String>  experienceLocations;
    @JsonProperty("created_at")
    private LocalDateTime updatedAt;
    private Boolean visible;
}
