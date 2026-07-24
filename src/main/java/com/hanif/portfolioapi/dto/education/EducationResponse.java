package com.hanif.portfolioapi.dto.education;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanif.portfolioapi.dto.common.ImageResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EducationResponse {
    private Long id;
    private String qualification;
    private String institution;
    private String level;
    private String grade;
    @JsonProperty("start_date")
    private LocalDate startDate;
    @JsonProperty("end_date")
    private LocalDate endDate;
    private String description;
    @JsonProperty("create_at")
    private LocalDateTime updatedAt;
    private List<ImageResponse> certificates;
}
