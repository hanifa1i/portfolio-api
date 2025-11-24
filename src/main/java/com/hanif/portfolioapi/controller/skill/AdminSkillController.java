package com.hanif.portfolioapi.controller.skill;

import com.hanif.portfolioapi.dto.skill.SkillRequest;
import com.hanif.portfolioapi.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/skills")
@RequiredArgsConstructor
public class AdminSkillController {

    private final SkillService skillService;

    @PostMapping("/create")
    public void createSkill(@Valid  @RequestBody SkillRequest skillRequest) {
        skillService.createSkill(skillRequest);
    }

    @PatchMapping("/{id}/update")
    public void updateSkill(@PathVariable Long id, @Valid @RequestBody SkillRequest skillRequest) {
        skillService.updateSkill(id, skillRequest);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
    }
}
