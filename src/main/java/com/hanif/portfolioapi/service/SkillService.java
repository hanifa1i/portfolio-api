package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.common.UpdateImagesRequest;
import com.hanif.portfolioapi.dto.skill.ExampleResponse;
import com.hanif.portfolioapi.dto.skill.SkillRequest;
import com.hanif.portfolioapi.dto.skill.SkillResponse;
import com.hanif.portfolioapi.enums.*;
import com.hanif.portfolioapi.exceptions.NotFoundException;
import com.hanif.portfolioapi.model.Skill;
import com.hanif.portfolioapi.model.SkillExample;
import com.hanif.portfolioapi.repository.SkillRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final ActivityLogService activityLogService;
    private final S3Service s3Service;

    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public SkillResponse getSkillById(Long id) {
        return skillRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, id)));
    }

    public Long createSkill(SkillRequest skillRequest) {
        Skill skill = mapToEntity(skillRequest);
        skillRepository.save(skill);

        activityLogService.addActivityLog(skill.getId(), EntityType.SKILL, ActionType.CREATE, null);

        return skill.getId();
    }

    public void updateSkill(Long id, SkillRequest skillRequest) {
        skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, id)));

        Skill skill = mapToEntity(skillRequest);
        skill.setId(id);
        skillRepository.save(skill);
        activityLogService.addActivityLog(skill.getId(), EntityType.SKILL, ActionType.UPDATE, null);

    }

    public String uploadExampleImage(Long id, MultipartFile file) throws IOException {

        String url = s3Service.uploadFile("skill", id, file);

        Skill updatedSkill = updateSkillUrl(id, url);

        skillRepository.save(updatedSkill);

        return url;
    }

    public List<String> updateSkillImages(Long id, UpdateImagesRequest request) {

        for (String url : request.getImages()) {
            Skill updatedSkill = updateSkillUrl(id, url);
            skillRepository.save(updatedSkill);
        }

        return request.getImages();
    }
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, id)));

        skill.getExamples().stream()
                .filter(example -> example.getType() == ExampleType.IMAGE)
                .map(SkillExample::getUrl)
                .map(this::extractKey)
                .forEach(s3Service::deleteFile);

        skillRepository.deleteById(id);

        activityLogService.addActivityLog(skill.getId(), EntityType.SKILL, ActionType.DELETE, null);

    }

    public String deleteExampleImage(Long skillId, Long exampleId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, skillId)));

        SkillExample example = getExample(skill, exampleId);

        String key = extractKey(example.getUrl());

        s3Service.deleteFile(key);

        skill.getExamples().remove(example);

        skillRepository.save(skill);

        return key;
    }

    private SkillExample getExample(Skill skill, Long exampleId) {
        return skill.getExamples()
                .stream()
                .filter(example -> example.getId().equals(exampleId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(
                        String.format(ResponseMessages.EXAMPLE_NOT_FOUND, exampleId)
                ));
    }

    private Skill updateSkillUrl(Long id, String url) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.SKILL_NOT_FOUND, id)));

        SkillExample example = SkillExample.builder()
                .skill(skill)
                .url(url)
                .type(ExampleType.IMAGE).build();

        List<SkillExample> skillExamples = skill.getExamples();

        skillExamples.add(example);

        return skill;
    }

    private SkillResponse toResponse(Skill skill) {
        SkillResponse response = SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .skillType(skill.getSkillType().getDisplayName())
                .updatedAt(skill.getUpdatedAt())
                .visible(skill.getVisible())
                .experienceLocations(
                        skill.getExperienceLocations().stream()
                                .map(ExperienceLocation::getDisplayName)
                                .toList())
                .build();

        List<ExampleResponse> exampleResponses = skill.getExamples().stream()
                .map(examples -> ExampleResponse.builder()
                        .id(examples.getId())
                        .exampleType(examples.getType())
                        .url(examples.getUrl())
                        .note(examples.getNote()).build()
                )
                .toList();

        response.setExamples(exampleResponses);

        return response;
    }

    private Skill mapToEntity(SkillRequest skillRequest) {

        Skill skill = Skill.builder()
                .name(skillRequest.getName())
                .description(skillRequest.getDescription())
                .skillType(SkillType.valueOf(skillRequest.getSkillType()))
                .experienceLocations(skillRequest.getExperienceLocations()
                        .stream()
                        .map(ExperienceLocation::valueOf)
                        .collect(Collectors.toSet()))
                .visible(skillRequest.getVisible())
                .build();

        List<SkillExample> examples = skillRequest.getExamples()
                .stream()
                .map(requestExample -> SkillExample.builder()
                        .type(requestExample.getType())
                        .url(requestExample.getUrl())
                        .note(requestExample.getNote())
                        .skill(skill)   // 👈 THIS IS THE IMPORTANT PART
                        .build()
                )
                .toList();

        skill.setExamples(examples);
        return skill;
    }

    private String extractKey(String imageUrl) {
        int index = imageUrl.indexOf(".com/") + 5;

        return imageUrl.substring(index);
    }
}
