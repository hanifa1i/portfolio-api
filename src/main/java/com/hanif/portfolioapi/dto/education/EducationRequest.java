package com.hanif.portfolioapi.dto.education;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EducationRequest {
    private String qualification;
    private String institution;
    private String level;
    private String grade;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private String description;
}
