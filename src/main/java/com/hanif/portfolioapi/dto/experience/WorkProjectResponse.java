package com.hanif.portfolioapi.dto.experience;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkProjectResponse {

    private Long id;
    private String title;
    private String description;
}
