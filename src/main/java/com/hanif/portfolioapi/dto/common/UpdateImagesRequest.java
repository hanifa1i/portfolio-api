package com.hanif.portfolioapi.dto.common;

import lombok.Data;

import java.util.List;

@Data
public class UpdateImagesRequest {
    private List<String> images;
}
