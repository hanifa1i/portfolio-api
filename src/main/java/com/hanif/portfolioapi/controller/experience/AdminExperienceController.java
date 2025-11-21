package com.hanif.portfolioapi.controller.experience;

import com.hanif.portfolioapi.dto.experience.ExperienceRequest;
import com.hanif.portfolioapi.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/experience")
@RequiredArgsConstructor
public class AdminExperienceController {

    private final ExperienceService experienceService;

    @PostMapping("/create")
    public void createExperience(@RequestBody ExperienceRequest experienceRequest) {
        experienceService.createExperience(experienceRequest);
    }

    @PatchMapping("/{id}/update")
    public void updateExperience(@PathVariable Long id, @RequestBody ExperienceRequest experienceRequest) {
        experienceService.updateExperience(id, experienceRequest);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperience(id);
    }
}
