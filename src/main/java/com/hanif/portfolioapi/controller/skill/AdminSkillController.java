package com.hanif.portfolioapi.controller.skill;

import com.hanif.portfolioapi.dto.common.ApiResponse;
import com.hanif.portfolioapi.dto.skill.SkillRequest;
import com.hanif.portfolioapi.service.SkillService;
import com.hanif.portfolioapi.validation.ResponseMessages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
