package com.hanif.portfolioapi.dto.tag;

import lombok.Data;

import java.util.List;

@Data
public class MultipleTagRequest {
    private List<TagRequest> tags;
}
