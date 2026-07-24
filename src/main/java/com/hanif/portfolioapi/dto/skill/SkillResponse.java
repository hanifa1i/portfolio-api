package com.hanif.portfolioapi.dto.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.enums.SkillType;
import com.hanif.portfolioapi.model.SkillExample;
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
    private String skillType;
    @JsonProperty("experience_locations")
    private List<String>  experienceLocations;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private Boolean visible;
    private List<ExampleResponse> examples;
}
