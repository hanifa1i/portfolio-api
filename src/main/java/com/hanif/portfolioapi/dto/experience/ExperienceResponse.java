package com.hanif.portfolioapi.dto.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ExperienceResponse {
    private Long id;
    @JsonProperty("job_title")
    private String jobTitle;
    @JsonProperty("company_name")
    private String companyName;
    private String description;
    private String location;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    @JsonProperty("created_at")
    private LocalDateTime updatedAt;

}
