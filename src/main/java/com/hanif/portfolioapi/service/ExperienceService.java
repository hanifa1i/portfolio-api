package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.experience.*;
import com.hanif.portfolioapi.enums.ActionType;
import com.hanif.portfolioapi.enums.EntityType;
import com.hanif.portfolioapi.exceptions.NotFoundException;
import com.hanif.portfolioapi.model.Experience;
import com.hanif.portfolioapi.model.WorkActivity;
import com.hanif.portfolioapi.model.WorkProject;
import com.hanif.portfolioapi.model.WorkSkill;
import com.hanif.portfolioapi.repository.ExperienceRepository;
import com.hanif.portfolioapi.validation.ResponseMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final ActivityLogService activityLogService;

    public List<ExperienceResponse> getAllExperience() {
        return experienceRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public ExperienceResponse getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EXPERIENCE_NOT_FOUND, id)));
    }

    public Long createExperience(ExperienceRequest experienceRequest) {
        Experience experience = mapToEntity(experienceRequest);
        experienceRepository.save(experience);
        activityLogService.addActivityLog(experience.getId(), EntityType.EXPERIENCE, ActionType.CREATE, null);

        return experience.getId();
    }

    public void updateExperience(Long id, ExperienceRequest experienceRequest) {
        experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EXPERIENCE_NOT_FOUND, id)));

        Experience experience = mapToEntity(experienceRequest);
        experience.setId(id);
        experienceRepository.save(experience);

        activityLogService.addActivityLog(experience.getId(), EntityType.EXPERIENCE, ActionType.UPDATE, null);
    }

    public void deleteExperience(Long id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ResponseMessages.EXPERIENCE_NOT_FOUND, id)));

        experienceRepository.deleteById(id);

        activityLogService.addActivityLog(experience.getId(), EntityType.EXPERIENCE, ActionType.DELETE, null);
    }

    private ExperienceResponse toResponse(Experience experience) {
        return ExperienceResponse.builder()
                .id(experience.getId())
                .jobTitle(experience.getJobTitle())
                .companyName(experience.getCompanyName())
                .description(experience.getDescription())
                .location(experience.getLocation())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .projects(
                        experience.getProjects().stream()
                                .map(project -> WorkProjectResponse.builder()
                                        .id(project.getId())
                                        .title(project.getTitle())
                                        .description(project.getDescription())
                                        .build()
                                ).toList())
                .activities(
                        experience.getActivities().stream()
                                .map(activity -> WorkActivityResponse.builder()
                                        .id(activity.getId())
                                        .activity(activity.getActivity())
                                        .description(activity.getDescription())
                                        .day(activity.getDay())
                                        .startTime(activity.getStartTime())
                                        .endTime(activity.getEndTime())
                                        .build()
                                ).toList())
                .skills(
                        experience.getSkills().stream()
                                .map(WorkSkill::getSkill)
                                .toList())
                .updatedAt(experience.getUpdatedAt())
                .build();

    }

    private Experience mapToEntity(ExperienceRequest request) {
        Experience experience = Experience.builder()
                .jobTitle(request.getJobTitle())
                .companyName(request.getCompanyName())
                .description(request.getDescription())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        if (request.getProjects() != null) {
            for (WorkProjectRequest projectRequest : request.getProjects()) {
                WorkProject project = WorkProject.builder()
                        .experience(experience)
                        .title(projectRequest.getTitle())
                        .description(projectRequest.getDescription())
                        .build();
                experience.addProject(project);
            }
        }
        if (experience.getActivities() != null) {
            for (WorkActivityRequest activityRequest : request.getActivities()) {
                WorkActivity activity = WorkActivity.builder()
                        .experience(experience)
                        .activity(activityRequest.getActivity())
                        .description(activityRequest.getDescription())
                        .day(activityRequest.getDay())
                        .startTime(activityRequest.getStartTime())
                        .endTime(activityRequest.getEndTime())
                        .build();
                experience.addActivity(activity);
            }
        }
        if (request.getSkills() != null) {
            for (String skillRequest : request.getSkills()) {
                WorkSkill skill = WorkSkill.builder()
                        .experience(experience)
                        .skill(skillRequest)
                        .build();
                experience.addSkill(skill);
            }
        }
        return experience;
    }

}
