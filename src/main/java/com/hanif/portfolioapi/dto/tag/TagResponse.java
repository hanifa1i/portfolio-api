package com.hanif.portfolioapi.dto.tag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagResponse {
    private Integer id;
    private String name;
    private String type;
}
