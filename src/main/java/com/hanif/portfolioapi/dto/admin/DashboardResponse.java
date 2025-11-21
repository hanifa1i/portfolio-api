package com.hanif.portfolioapi.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    @JsonProperty("total_artworks")
    long totalArtworks;
    @JsonProperty("total_skills")
    long totalSkills;
    @JsonProperty("total_experience")
    long totalExperience;
    @JsonProperty("total_qualifications")
    long totalQualifications;

}
