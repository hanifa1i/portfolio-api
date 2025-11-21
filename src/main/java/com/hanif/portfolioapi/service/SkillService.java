package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.skill.SkillRequest;
import com.hanif.portfolioapi.dto.skill.SkillResponse;
import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.model.Skill;
import com.hanif.portfolioapi.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public void createSkill(SkillRequest skillRequest) {
        Skill skill = mapToEntity(skillRequest);
        skillRepository.save(skill);
    }

    public void updateSkill(Long id, SkillRequest skillRequest) {
        Skill skill = mapToEntity(skillRequest);
        skill.setId(id);
        skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }

    private SkillResponse toResponse(Skill skill) {
        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .skillType(skill.getSkillType())
                .updatedAt(skill.getUpdatedAt())
                .visible(skill.getVisible())
                .experienceLocations(
                        skill.getExperienceLocations().stream()
                                .map(ExperienceLocation::getDisplayName)
                                .toList())
                .build();
    }

    private Skill mapToEntity(SkillRequest skillRequest) {
        return Skill.builder()
                .name(skillRequest.getName())
                .description(skillRequest.getDescription())
                .skillType(skillRequest.getSkillType())
                .experienceLocations(skillRequest.getExperienceLocations())
                .visible(skillRequest.getVisible())
                .build();
    }

}
