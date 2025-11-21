package com.hanif.portfolioapi.controller.experience;

import com.hanif.portfolioapi.dto.experience.ExperienceResponse;
import com.hanif.portfolioapi.dto.skill.SkillResponse;
import com.hanif.portfolioapi.service.ExperienceService;
import com.hanif.portfolioapi.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getExperience() {
        return ResponseEntity.ok(experienceService.getAllExperience());
    }
}
