package com.hanif.portfolioapi.controller.education;

import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.dto.education.EducationRequest;
import com.hanif.portfolioapi.service.EducationService;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/education")
@RequiredArgsConstructor
public class AdminEducationController {

    private final EducationService educationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEducation(@Valid  @RequestBody EducationRequest educationRequest) {
        Long id = educationService.createEducation(educationRequest);

        return ApiResponse.success(ResponseMessages.EDUCATION_CREATED, id);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateEducation(@PathVariable Long id, @Valid @RequestBody EducationRequest educationRequest) {
        educationService.updateEducation(id, educationRequest);

        return ApiResponse.success(ResponseMessages.EDUCATION_UPDATED, id);

    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteEducation(@PathVariable Long id) {
        educationService.deleteEducation(id);

        return ApiResponse.success(ResponseMessages.EDUCATION_DELETED, id);
    }

}
