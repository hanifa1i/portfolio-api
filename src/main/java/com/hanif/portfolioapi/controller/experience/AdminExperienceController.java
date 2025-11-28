package com.hanif.portfolioapi.controller.experience;

import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.dto.experience.ExperienceRequest;
import com.hanif.portfolioapi.service.ExperienceService;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/experience")
@RequiredArgsConstructor
public class AdminExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createExperience(@Valid  @RequestBody ExperienceRequest experienceRequest) {
        Long id = experienceService.createExperience(experienceRequest);

        return ApiResponse.success(ResponseMessages.EXPERIENCE_CREATED, id);

    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateExperience(@PathVariable Long id, @Valid @RequestBody ExperienceRequest experienceRequest) {
        experienceService.updateExperience(id, experienceRequest);

        return ApiResponse.success(ResponseMessages.EXPERIENCE_UPDATED, id);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);

        return ApiResponse.success(ResponseMessages.EXPERIENCE_DELETED, id);
    }
}
