package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.skill.SkillRequest;
import com.hanif.portfolioapi.dto.skill.SkillResponse;
import com.hanif.portfolioapi.enums.ExperienceLocation;
import com.hanif.portfolioapi.enums.SkillType;
import com.hanif.portfolioapi.exceptions.NotFoundException;
import com.hanif.portfolioapi.model.Skill;
import com.hanif.portfolioapi.repository.SkillRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public Long createSkill(SkillRequest skillRequest) {
        Skill skill = mapToEntity(skillRequest);
        skillRepository.save(skill);

        return skill.getId();
    }

    public void updateSkill(Long id, SkillRequest skillRequest) {
        skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, id)));

        Skill skill = mapToEntity(skillRequest);
        skill.setId(id);
        skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, id)));

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
                .skillType(SkillType.valueOf(skillRequest.getSkillType()))
                .experienceLocations(skillRequest.getExperienceLocations()
                        .stream()
                        .map(ExperienceLocation::valueOf)
                        .collect(Collectors.toSet()))
                .visible(skillRequest.getVisible())
                .build();
    }

}
