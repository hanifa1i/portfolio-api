package com.hanif.portfolioapi.dto.skill;

import com.hanif.portfolioapi.enums.ExampleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleResponse {
    private Long id;
    private ExampleType exampleType;
    private String url;
    private String note;
}
