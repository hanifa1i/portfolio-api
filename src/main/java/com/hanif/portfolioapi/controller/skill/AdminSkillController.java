package com.hanif.portfolioapi.controller.skill;

import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.dto.common.UpdateImagesRequest;
import com.hanif.portfolioapi.dto.skill.SkillRequest;
import com.hanif.portfolioapi.service.SkillService;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/skills")
@RequiredArgsConstructor
public class AdminSkillController {

    private final SkillService skillService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createSkill(@Valid  @RequestBody SkillRequest skillRequest) {
        Long id = skillService.createSkill(skillRequest);

        return ApiResponse.success(ResponseMessages.SKILL_CREATED, id);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest skillRequest) {
        skillService.updateSkill(id, skillRequest);

        return ApiResponse.success(ResponseMessages.SKILL_UPDATED, id);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);

        return ApiResponse.success(ResponseMessages.SKILL_DELETED, id);
    }

    @PostMapping("/{id}/image/upload")
    public ResponseEntity<ApiResponse> uploadExampleImage(@PathVariable Long id,
                                                        @RequestParam("image") MultipartFile image) throws IOException {
        String imageUrl = skillService.uploadExampleImage(id, image);

        return ApiResponse.success(imageUrl, null);
    }

    @PostMapping("/{id}/image/update")
    public ResponseEntity<ApiResponse> updateExampleImage(@PathVariable Long id, @RequestBody UpdateImagesRequest request){

        List<String> imageUrls = skillService.updateSkillImages(id, request);

        return ApiResponse.success(imageUrls.toString(), id);
    }

    @DeleteMapping("/{skillId}/image/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteExampleImage(@PathVariable Long skillId, @PathVariable Long imageId) {

        String key = skillService.deleteExampleImage(skillId, imageId);

        return ApiResponse.success("Example deleted: " + key, null);
    }
}
