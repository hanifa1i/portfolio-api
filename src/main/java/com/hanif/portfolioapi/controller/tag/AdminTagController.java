package com.hanif.portfolioapi.controller.tag;

import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.dto.tag.MultipleTagRequest;
import com.hanif.portfolioapi.service.TagService;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/tags")
@RequiredArgsConstructor
public class AdminTagController {

    private final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMultipleTags(@Valid @RequestBody MultipleTagRequest multipleTagRequest) {
        tagService.createMultipleTags(multipleTagRequest);

        return ApiResponse.success(ResponseMessages.TAGS_CREATED, null);
    }
}
