package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.dto.experience.ExperienceRequest;
import com.hanif.portfolioapi.dto.experience.ExperienceResponse;
import com.hanif.portfolioapi.model.Experience;
import com.hanif.portfolioapi.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public List<ExperienceResponse> getAllExperience() {
        return experienceRepository.findAll().stream()
                .map(this::toResponse).toList();
    }

    public void createExperience(ExperienceRequest experienceRequest) {
        Experience experience = mapToEntity(experienceRequest);
        experienceRepository.save(experience);
    }

    public void updateExperience(Long id, ExperienceRequest experienceRequest) {
        Experience experience = mapToEntity(experienceRequest);
        experience.setId(id);
        experienceRepository.save(experience);
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
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
                .updatedAt(experience.getUpdatedAt())
                .build();
    }

    private Experience mapToEntity(ExperienceRequest experienceRequest) {
        return Experience.builder()
                .jobTitle(experienceRequest.getJobTitle())
                .companyName(experienceRequest.getCompanyName())
                .description(experienceRequest.getDescription())
                .location(experienceRequest.getLocation())
                .startDate(experienceRequest.getStartDate())
                .endDate(experienceRequest.getEndDate())
                .build();
    }

}
