package com.hanif.portfolioapi.dto.experience;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkActivityResponse {

    private Long id;
    private String activity;
    private String description;
    private String day;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("end_time")
    private String endTime;
}
