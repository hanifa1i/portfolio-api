package com.hanif.portfolioapi.dto.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceRequest {
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
}
